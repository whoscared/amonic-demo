package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.domain.info.Ticket;
import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Flight;
import com.whoscared.amonic.domain.utils.Passenger;
import com.whoscared.amonic.security.PersonDetails;
import com.whoscared.amonic.services.PersonService;
import com.whoscared.amonic.services.ScheduleService;
import com.whoscared.amonic.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/flight")
public class FlightController {

    private final ScheduleService scheduleService;
    private final PersonService personService;
    private final TicketService ticketService;

    @Autowired
    public FlightController(ScheduleService scheduleService, PersonService personService, TicketService ticketService) {
        this.scheduleService = scheduleService;
        this.personService = personService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    public String mainFlightPage(Model model) {
        model.addAttribute("flight", new Flight());
        model.addAttribute("threeOutbound", false);
        model.addAttribute("threeReturn", false);
        return "flight/search";
    }

    @PostMapping()
    public String searchWithFilters(@ModelAttribute("flight") Flight flight,
                                    @ModelAttribute("threeOutbound") Boolean threeOutbound,
                                    @ModelAttribute("threeReturn") Boolean threeReturn,
                                    Model model) {
        model.addAttribute("flight", flight);
        model.addAttribute("outboundList", scheduleService.findOutboundByFlight(flight, threeOutbound));
        model.addAttribute("returnList", scheduleService.findReturnByFlight(flight, threeReturn));
        return "flight/search";
    }

    @PostMapping("/book_flight")
    public String bookFlight(@ModelAttribute("scheduleOutbound") Schedule scheduleOutbound,
                             @ModelAttribute("scheduleReturn") Schedule scheduleReturn,
                             Model model) {
        model.addAttribute("scheduleOutbound", scheduleOutbound);
        model.addAttribute("scheduleReturn", scheduleReturn);
        model.addAttribute("ticket", new Ticket());
        return "flight/book_flight";
    }

    @GetMapping("/book_flight")
    public String bookFlightGet(@ModelAttribute("scheduleOutbound") Schedule scheduleOutbound,
                                @ModelAttribute("scheduleReturn") Schedule scheduleReturn,
                                @ModelAttribute("passengerList") List<Passenger> passengerList,
                                Model model) {
        model.addAttribute("scheduleOutbound", scheduleOutbound);
        model.addAttribute("scheduleReturn", scheduleReturn);
        model.addAttribute("ticket", new Ticket());
        return "flight/book_flight";
    }

    @PostMapping("/book_flight/add_passenger")
    public String addPassenger(@ModelAttribute("scheduleOutbound") Schedule scheduleOutbound,
                               @ModelAttribute("scheduleReturn") Schedule scheduleReturn,
                               @ModelAttribute("passengerList") List<Passenger> passengerList,
                               @ModelAttribute("passenger") Passenger passenger,
                               Model model) {
        passengerList.add(passenger);
        model.addAttribute("passengerList", passengerList);

        return "redirect:/flight//book_flight";
    }

    @PostMapping("/book_flight/add_passenger/confim")
    public String confimBooking(@ModelAttribute("scheduleOutbound") Schedule scheduleOutbound,
                                @ModelAttribute("scheduleReturn") Schedule scheduleReturn,
                                @ModelAttribute("ticket") Ticket ticket,
                                @ModelAttribute("passengers") int passengers,
                                @ModelAttribute("passengerList") List<Passenger> passengerList,
                                @ModelAttribute("passenger") Passenger passenger,
                                @ModelAttribute("booking") String bookingReference) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personService.findByEmail(personDetails.getUsername());
        ticket.setUser(person);
        ticket.setBookingReference(bookingReference);
        for (Passenger temp : passengerList){
            ticket.setPassanger(passenger);
            ticketService.save(ticket);
        }
        return "flight/billing_confirmation";
    }
}

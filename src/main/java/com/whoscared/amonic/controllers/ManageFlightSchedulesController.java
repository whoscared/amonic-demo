package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.info.Confirmed;
import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.repositories.AirportRepository;
import com.whoscared.amonic.services.ScheduleService;
import com.whoscared.amonic.utils.FlightSchedulesFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/manage_flight_schedules")
public class ManageFlightSchedulesController {

    private final ScheduleService scheduleService;
    private final AirportRepository airportRepository;

    @Autowired
    public ManageFlightSchedulesController(ScheduleService scheduleService, AirportRepository airportRepository) {
        this.scheduleService = scheduleService;
        this.airportRepository = airportRepository;
    }

    @GetMapping
    public String manageFlightSchedules(@ModelAttribute("error") String error,
            Model model) {
        if (error != null){
            model.addAttribute("error", error);
        }
        List<Schedule> allSchedules = scheduleService.findAll()
                .stream().sorted((x, y) -> {
                    if (x.getDate().compareTo(y.getDate()) == 0)
                        return x.getFlightTime().compareTo(y.getFlightTime());
                    return x.getDate().compareTo(y.getDate());
                }).toList();
        model.addAttribute("scheduleList", allSchedules);
        model.addAttribute("airportList", airportRepository.findAll());
        model.addAttribute("filter", new FlightSchedulesFilter());
        return "schedule/manage_flight_schedules";
    }

    @PostMapping
    public String manageFlightSchedulesFilter(@ModelAttribute("filter") FlightSchedulesFilter filter,
                                              Model model) {

        List<Schedule> allSchedules = scheduleService.findAll();
        model.addAttribute("airportList", airportRepository.findAll());

        if (filter.getFrom() != null && filter.getTo() != null
                && filter.getFrom().equals(filter.getTo())) {
            model.addAttribute("scheduleList", allSchedules);
            model.addAttribute("error", "Departure airport and arrival airport cannot match");
            model.addAttribute("filter", new FlightSchedulesFilter());
            return "schedule/manage_flight_schedules";
        }

        if (filter.getFrom() != null) {
            allSchedules = allSchedules.stream()
                    .filter(x -> x.getRoute().getDepartureAirport().equals(filter.getFrom()))
                    .toList();
        }

        if (filter.getTo() != null) {
            allSchedules = allSchedules.stream()
                    .filter(x -> x.getRoute().getArrivalAirport().equals(filter.getTo()))
                    .toList();
        }

        if (filter.getOutbound() != null) {
            allSchedules = allSchedules.stream()
                    .filter(x -> x.getDate().getYear() == filter.getOutbound().getYear())
                    .filter(x -> x.getDate().getMonth() == filter.getOutbound().getMonth())
                    .filter(x -> x.getDate().getDay() == filter.getOutbound().getDay())
                    .toList();
        }

        if (filter.getFlightNumber() != null && !(filter.getFlightNumber().isEmpty())) {
            allSchedules = allSchedules.stream()
                    .filter(x -> x.getFlightNumber().equals(filter.getFlightNumber()))
                    .toList();
        }

        if (filter.getSort() != null) {
            switch (filter.getSort()) {
                case CONFIRMED -> allSchedules = allSchedules.stream()
                        .sorted((x, y) -> {
                            if (x.getConfirmed().equals(y.getConfirmed()))
                                return 0;
                            if (x.getConfirmed().equals(Confirmed.CONFIRMED)
                                    && x.getConfirmed().equals(Confirmed.NOT_CONFIRMED))
                                return 1;
                            return -1;
                        }).toList();
                case DATE_TIME -> allSchedules = allSchedules.stream()
                        .sorted((x, y) -> {
                            if (x.getDate().compareTo(y.getDate()) == 0)
                                return x.getFlightTime().compareTo(y.getFlightTime());
                            return x.getDate().compareTo(y.getDate());
                        }).toList();
                case ECONOMY_PRICE -> allSchedules = allSchedules.stream()
                        .sorted(Comparator.comparing(Schedule::getEconomyPrice))
                        .toList();
            }
        }

        model.addAttribute("scheduleList", allSchedules);

        return "schedule/manage_flight_schedules";
    }
}

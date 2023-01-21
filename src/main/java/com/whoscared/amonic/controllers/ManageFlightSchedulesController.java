package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.info.Confirmed;
import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.services.ScheduleService;
import com.whoscared.amonic.utils.FlightSchedulesFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/manage_flight_schedules")
public class ManageFlightSchedulesController {

    private final ScheduleService scheduleService;

    @Autowired
    public ManageFlightSchedulesController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public String manageFlightSchedules(Model model) {
        List<Schedule> allSchedules = scheduleService.findAll()
                .stream().sorted((x, y) -> {
                    if (x.getDate().compareTo(y.getDate()) == 0)
                        return x.getTime().compareTo(y.getTime());
                    return x.getDate().compareTo(y.getDate());
                }).toList();
        model.addAttribute("scheduleList", allSchedules);
        model.addAttribute("filter", new FlightSchedulesFilter());
        return "schedule/manage_flight_schedules";
    }

    public String manageFlightSchedulesFilter(@ModelAttribute("filter") FlightSchedulesFilter filter,
                                              Model model) {

        if (filter.getFrom() != null && filter.getTo() != null
                && filter.getFrom() == filter.getTo()) {
            model.addAttribute("error", "Departure airport and arrival airport cannot match");
            return "redirect:/manage_flight_schedules";
        }

        List<Schedule> allSchedules = scheduleService.findAll();

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
                    .filter(x -> x.getDate().equals(filter.getOutbound()))
                    .toList();
        }

        if (filter.getFlight_number() != null) {
            allSchedules = allSchedules.stream()
                    .filter(x -> x.getFlightNumber().equals(filter.getFlight_number()))
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
                                return x.getTime().compareTo(y.getTime());
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

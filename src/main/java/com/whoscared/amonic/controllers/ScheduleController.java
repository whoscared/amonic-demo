package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.repositories.AirportRepository;
import com.whoscared.amonic.services.ScheduleService;
import com.whoscared.amonic.utils.FlightSchedulesFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final AirportRepository airportRepository;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, AirportRepository airportRepository) {
        this.scheduleService = scheduleService;
        this.airportRepository = airportRepository;
    }

    @GetMapping("/{id}")
    public String edit (@PathVariable("id") Long id,
                        Model model){
        Schedule schedule = scheduleService.findById(id);
        if (schedule == null){
            model.addAttribute("error", "Schedule not found!");
        }
        else {
            model.addAttribute("schedule", schedule);
        }
        return "schedule/schedule_edit";
    }

    @PostMapping("/{id}")
    public String save (@PathVariable("id") Long id,
                        @ModelAttribute("schedule") Schedule schedule){
        scheduleService.edit(id, schedule.getDate(), schedule.getFlightTime(), schedule.getEconomyPrice());
        return "redirect:/manage_flight_schedules";
    }
}

package com.whoscared.amonic.utils;

import com.whoscared.amonic.domain.info.*;
import com.whoscared.amonic.repositories.AircraftRepository;
import com.whoscared.amonic.repositories.AirportRepository;
import com.whoscared.amonic.repositories.RouteRepository;
import com.whoscared.amonic.services.ScheduleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

@Component
public class ScheduleChangesHandling {

    private final ScheduleService scheduleService;
    private final AircraftRepository aircraftRepository;

    private final AirportRepository airportRepository;

    private final String success = "Successful Changes Applied";
    private final String duplicate = "Duplicate Records Discarded";
    private final String miss = "Record with missing fields discarded";
    private final HashMap<String, Integer> changesResult = new HashMap<>();
    private final RouteRepository routeRepository;

    {
        changesResult.put(success, 0);
        changesResult.put(duplicate, 0);
        changesResult.put(miss, 0);
    }

    @Autowired
    public ScheduleChangesHandling(ScheduleService scheduleService, AircraftRepository aircraftRepository, AirportRepository airportRepository,
                                   RouteRepository routeRepository) {
        this.scheduleService = scheduleService;
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.routeRepository = routeRepository;
    }

    public HashMap<String, Integer> handling(String file) {
        String[] results = file.split("\n");
        for (String result : results) {
            addSchedule(result);
        }
        return changesResult;
    }

    // 0 - action
    // 1 - date
    // 2 - time
    // 3 - flight number
    // 4, 5 - airport IATA code
    // 6 - aircraft
    // 7 - economy price
    // 8 - confirmed
    public void addSchedule(String schedule) {
        schedule = schedule.replace("\r", "");
        String[] request = schedule.split(" ");

        if (request.length != 9) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        Schedule newSchedule = new Schedule();

        try {
            Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(request[1]);
            newSchedule.setDate(newDate);
        } catch (ParseException e) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        try {
            LocalTime newTime = LocalTime.parse(request[2]);
            newSchedule.setFlightTime(new Time(newTime.getHour(), newTime.getMinute(), newTime.getSecond()));
        } catch (DateTimeParseException e) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        try {
            newSchedule.setFlightNumber(String.valueOf(Integer.parseInt(request[3])));
        } catch (NumberFormatException e) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }


        if (request[4] != null && !request[4].isEmpty()
                && request[5] != null && !request[5].isEmpty()) {
            Airport from = airportRepository.findByIATACode(request[4]).orElse(null);

            Airport to = airportRepository.findByIATACode(request[5]).orElse(null);
            //if
            Route newRoute = routeRepository.findByArrivalAirportAndDepartureAirport(from, to).orElse(null);
            if (newRoute != null) {
                newSchedule.setRoute(newRoute);
            } else {
                changesResult.put(miss, changesResult.get(miss) + 1);
                return;
            }
        }

        try {
            Long newAircraftId = Long.parseLong(request[6]);
            Aircraft newAircraft = aircraftRepository.findById(newAircraftId).orElse(null);
            if (newAircraft != null) {
                newSchedule.setAircraft(newAircraft);
            } else {
                changesResult.put(miss, changesResult.get(miss) + 1);
                return;
            }
        } catch (NumberFormatException e) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        try {
            BigDecimal newEconomyPrice = BigDecimal.valueOf(Long.parseLong(request[7]));
            newSchedule.setEconomyPrice(newEconomyPrice);
        } catch (NumberFormatException e) {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        if (request[8].equals("OK")) {
            newSchedule.setConfirmed(Confirmed.CONFIRMED);
        } else if (request[8].equals("CANCELED")) {
            newSchedule.setConfirmed(Confirmed.NOT_CONFIRMED);
        } else {
            changesResult.put(miss, changesResult.get(miss) + 1);
            return;
        }

        if (scheduleService.findAll().stream()
                .anyMatch(x -> x.equals(newSchedule))) {
            changesResult.put(duplicate, changesResult.get(duplicate) + 1);
            return;
        }

        switch (request[0]) {
            case "ADD" -> {
                scheduleService.save(newSchedule);
                changesResult.put(success, changesResult.get(success) + 1);
            }
            case "EDIT" -> {
                Schedule prevSchedule = scheduleService.findByFlightNumberAndDate(newSchedule.getFlightNumber(), newSchedule.getDate());
                if (prevSchedule != null) {
                    scheduleService.update(prevSchedule.getId(), newSchedule);
                } else {
                    changesResult.put(miss, changesResult.get(miss) + 1);
                    return;
                }
            }
            default -> {
                changesResult.put(miss, changesResult.get(miss) + 1);
                return;
            }
        }
    }

    public void cleanChangesResult (){
        changesResult.put(success, 0);
        changesResult.put(miss, 0);
        changesResult.put(duplicate, 0);
    }

    public int getSuccess () {
        return changesResult.get(success);
    }

    public int getMiss () {
        return changesResult.get(miss);
    }

    public int getDuplicate () {
        return changesResult.get(duplicate);
    }

    public String getSuccessMessage() {
        return success;
    }

    public String getMissMessage() {
        return miss;
    }

    public String getDuplicateMessage() {
        return duplicate;
    }

    public HashMap<String, Integer> getChangesResult() {
        return changesResult;
    }

}

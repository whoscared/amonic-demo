package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.domain.utils.Flight;
import com.whoscared.amonic.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public void update(Long id, Schedule schedule) {
        schedule.setId(id);
        scheduleRepository.save(schedule);
    }

    public Schedule findByFlightNumberAndDate(String flightNumber, Date date) {
        return scheduleRepository.findByFlightNumberAndDate(flightNumber, date).orElse(null);
    }

    public Schedule findById (Long id){
        return scheduleRepository.findById(id).orElse(null);
    }
    public void edit (Long id, Date date, Time flightTime, BigDecimal economyPrice){
        Schedule current = findById(id);
        current.setDate(date);
        current.setFlightTime(flightTime);
        current.setEconomyPrice(economyPrice);
        update(id, current);
    }

    public List<Schedule> findOutboundByFlight (Flight flight, boolean threeOutbound){
        List<Schedule> scheduleList = findAll().stream()
                .filter(x -> flight.getFrom() == null || x.getRoute().getArrivalAirport().getId().equals(flight.getFrom().getId()))
                .filter(x -> flight.getTo() == null || x.getRoute().getDepartureAirport().getId().equals(flight.getTo().getId()))
                .toList();
        if (flight.getOutboundDate() != null){
            scheduleList = scheduleList.stream()
                    .filter(x -> (threeOutbound)
                            ? (x.getDate().getTime() >= (flight.getOutboundDate().getTime() - 259200000L)
                            && x.getDate().getTime() <= (flight.getOutboundDate().getTime() + 259200000L))
                            : (x.getDate().equals(flight.getOutboundDate()))).toList();
        }
        return scheduleList;
    }

    public List<Schedule> findReturnByFlight (Flight flight, boolean threeReturn){
        List<Schedule> scheduleList = findAll().stream()
                .filter(x -> flight.getFrom() == null || x.getRoute().getDepartureAirport().getId().equals(flight.getFrom().getId()))
                .filter(x -> flight.getTo() == null || x.getRoute().getArrivalAirport().getId().equals(flight.getTo().getId()))
                .toList();
        if (flight.getReturnDate() != null){
            scheduleList = scheduleList.stream()
                    .filter(x -> (threeReturn)
                            ? (x.getDate().getTime() >= (flight.getReturnDate().getTime() - 259200000L)
                            && x.getDate().getTime() <= (flight.getReturnDate().getTime() + 259200000L))
                            : (x.getDate().equals(flight.getReturnDate()))).toList();
        }
        return scheduleList;
    }
}

package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.info.Schedule;
import com.whoscared.amonic.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

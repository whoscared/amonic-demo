package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.info.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAll();

    Optional<Schedule> findByFlightNumberAndDate(String flightNumber, Date date);

}

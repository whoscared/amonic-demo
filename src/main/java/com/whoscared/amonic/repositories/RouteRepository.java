package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.info.Airport;
import com.whoscared.amonic.domain.info.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByArrivalAirportAndDepartureAirport(Airport from, Airport to);
}

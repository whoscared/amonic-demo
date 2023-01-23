package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.info.Airport;
import com.whoscared.amonic.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> findAll (){
        return airportRepository.findAll();
    }
}

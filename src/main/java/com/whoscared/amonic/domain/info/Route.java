package com.whoscared.amonic.domain.info;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;
    @OneToOne
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    private Airport arrivalAirport;
    @Column(name = "distance")
    private double distance;
    @Column(name = "flight_time")
    private Time flightTime;

    public Route() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Time getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Time flightTime) {
        this.flightTime = flightTime;
    }
}

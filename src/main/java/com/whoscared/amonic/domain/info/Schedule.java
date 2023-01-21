package com.whoscared.amonic.domain.info;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "time")
    private Time time;
    @OneToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private Aircraft aircraft;
    @OneToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;
    @Column(name = "economy_price")
    private BigDecimal economyPrice;
    @Column(name = "confirmed")
    @Enumerated(EnumType.ORDINAL)
    private Confirmed confirmed;
    @Column(name = "flight_number")
    private String flightNumber;

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public BigDecimal getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(BigDecimal economyPrice) {
        this.economyPrice = economyPrice;
    }

    public Confirmed getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Confirmed confirmed) {
        this.confirmed = confirmed;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}

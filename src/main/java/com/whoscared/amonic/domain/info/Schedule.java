package com.whoscared.amonic.domain.info;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Column(name = "flight_time")
    private Time flightTime;
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

    public Time getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Time flightTime) {
        this.flightTime = flightTime;
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

    public BigDecimal getBusinessPrice(){
        return economyPrice.multiply(new BigDecimal("1.35")).setScale(0, RoundingMode.FLOOR);
    }
    public BigDecimal getFirstClassPrice(){
        return getBusinessPrice().multiply(new BigDecimal("1.30")).setScale(0, RoundingMode.FLOOR);
    }

    @Override
    public boolean equals(Object obj) {
        Schedule schedule = (Schedule) obj;
        if (!(new Date(date.getYear(), date.getMonth(), date.getDay()))
                .equals(new Date(schedule.date.getYear(), schedule.date.getMonth(), schedule.date.getDay()))){
            return false;
        }
        if (!flightTime.equals(schedule.flightTime)){
            return false;
        }
        if (!aircraft.getId().equals(schedule.aircraft.getId())){
            return false;
        }
        if (!route.getId().equals(schedule.route.getId())){
            return false;
        }
        if (!economyPrice.equals(schedule.economyPrice)){
            return false;
        }
        if (!confirmed.equals(schedule.confirmed)){
            return false;
        }
        if (!flightNumber.equals(schedule.flightNumber)){
            return false;
        }
        return true;
    }
}

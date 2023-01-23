package com.whoscared.amonic.utils;

import com.whoscared.amonic.domain.info.Airport;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FlightSchedulesFilter {
    private Airport from;
    private Airport to;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outbound;
    private String flightNumber;
    private SortForFlightSchedules sort;

    public FlightSchedulesFilter() {
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public Date getOutbound() {
        return outbound;
    }

    public void setOutbound(Date outbound) {
        this.outbound = outbound;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public SortForFlightSchedules getSort() {
        return sort;
    }

    public void setSort(SortForFlightSchedules sort) {
        this.sort = sort;
    }
}

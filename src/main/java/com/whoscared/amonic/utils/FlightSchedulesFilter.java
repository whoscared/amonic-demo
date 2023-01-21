package com.whoscared.amonic.utils;

import com.whoscared.amonic.domain.info.Airport;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FlightSchedulesFilter {
    private Airport from;
    private Airport to;
    private Date outbound;
    private String flight_number;
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

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public SortForFlightSchedules getSort() {
        return sort;
    }

    public void setSort(SortForFlightSchedules sort) {
        this.sort = sort;
    }
}

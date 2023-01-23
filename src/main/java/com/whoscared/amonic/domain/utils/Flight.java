package com.whoscared.amonic.domain.utils;

import com.whoscared.amonic.domain.info.Airport;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Flight {
    private Airport from;
    private Airport to;
    private TypeOfCabin cabinType;
    private boolean returnFlight;
    private Date outboundDate;
    private Date returnDate;

    public Flight(){}

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

    public TypeOfCabin getCabinType() {
        return cabinType;
    }

    public void setCabinType(TypeOfCabin cabinType) {
        this.cabinType = cabinType;
    }

    public boolean isReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(boolean returnFlight) {
        this.returnFlight = returnFlight;
    }

    public Date getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(Date outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

package com.whoscared.amonic.domain.info;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class Schedules {
    private Long id;
    private Date date;
    private Time time;
    private Aircraft aircraft;
    private Route route;
    private String flightNumber;
    private BigDecimal economyPrice;
    private Confirmed confirmed;
}

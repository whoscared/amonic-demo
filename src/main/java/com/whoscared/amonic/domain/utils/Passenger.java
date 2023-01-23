package com.whoscared.amonic.domain.utils;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class Passenger {
    private String firstname;
    private String lastname;
    private String phone;
    private String passportNumber;
    private Country passportCountry;

    public Passenger(){}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Country getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(Country passportCountry) {
        this.passportCountry = passportCountry;
    }
}

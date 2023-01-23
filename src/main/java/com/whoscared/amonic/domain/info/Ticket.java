package com.whoscared.amonic.domain.info;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Country;
import com.whoscared.amonic.domain.utils.Passenger;
import com.whoscared.amonic.domain.utils.TypeOfCabin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person user;
    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
    @Column(name = "cabin_type")
    @Enumerated(EnumType.STRING)
    private TypeOfCabin typeOfCabin;
    @Column(name = "firstname")
    @Size(max = 50, message = "Firstname must be shorter than 50 characters")
    @NotEmpty(message = "Should not be empty!")
    private String firstname;
    @Column(name = "lastname")
    @Size(max = 50, message = "Lastname must be shorter than 50 characters")
    @NotEmpty(message = "Should not be empty!")
    private String lastname;
    @Column(name = "phone")
    @Size(max = 14, message = "Phone must be shorter than 14 characters")
    @NotEmpty(message = "Should not be empty!")
    private String phone;
    @Column(name = "passport_number")
    @Size(max = 9, message = "Passport number must be shorter than 9 characters")
    @NotEmpty(message = "Should not be empty!")
    private String passportNumber;
    @OneToOne
    @JoinColumn(name = "passport_country_id", referencedColumnName = "id")
    private Country passportCountry;
    @Column(name = "booking_reference")
    @Size(max = 6, message = "Booking reference must be shorter than 6 characters")
    @NotEmpty(message = "Should not be empty!")
    private String bookingReference;
    @Column(name = "confirmed")
    @Enumerated(EnumType.ORDINAL)
    private Confirmed confirmed;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public TypeOfCabin getTypeOfCabin() {
        return typeOfCabin;
    }

    public void setTypeOfCabin(TypeOfCabin typeOfCabin) {
        this.typeOfCabin = typeOfCabin;
    }

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

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Confirmed getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Confirmed confirmed) {
        this.confirmed = confirmed;
    }

    public void setPassanger (Passenger passenger){
        this.firstname = passenger.getFirstname();
        this.lastname = passenger.getLastname();
        this.phone = passenger.getPhone();
        this.passportCountry = passenger.getPassportCountry();
        this.passportCountry = passenger.getPassportCountry();
    }
}

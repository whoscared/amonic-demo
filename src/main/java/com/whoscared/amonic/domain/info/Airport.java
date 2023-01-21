package com.whoscared.amonic.domain.info;

import com.whoscared.amonic.domain.utils.Country;
import jakarta.persistence.*;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
    @Column(name = "IATA_code")
    private String IATACode;
    @Column(name = "name")
    private String name;

    public Airport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

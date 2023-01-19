package com.whoscared.amonic.domain.utils;

import com.whoscared.amonic.domain.person.Title;
import jakarta.persistence.*;

@Entity
@Table(name = "office")
public class Office {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;
    @Column(name = "title")
    @Enumerated(EnumType.STRING)
    private Title title;
    @Column(name = "phone")
    private String phone;
    @Column(name = "contact")
    private String contact;
}

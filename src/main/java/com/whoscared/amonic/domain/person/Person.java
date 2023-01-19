package com.whoscared.amonic.domain.person;

import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.domain.utils.Office;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @OneToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;

    @Column(name = "access")
    @Enumerated(EnumType.STRING)
    private TypeOfAccess access;

    @Column(name = "email")
    @Email(message = "Incorrect email")
    @NotEmpty
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "lastname")
    @NotEmpty
    @Size(min = 2, max = 50, message = "Lastname cannot be more than 50 characters")
    private String lastname;
    @Column(name = "firstname")
    @NotEmpty
    @Size(min = 2, max = 50, message = "Firstname cannot be more than 50 characters")
    private String firstname;
    @Column(name = "birthdate")
    @NotEmpty
    private Date birthdate;
    @OneToMany(mappedBy = "person")
    private List<Activity> activity;

    private String timeOnSystem;

    public Person() {
    }

    ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public TypeOfAccess getAccess() {
        return access;
    }

    public void setAccess(TypeOfAccess access) {
        this.access = access;
    }

    public String getTimeOnSystem() {
        return timeOnSystem;
    }

    public void setTimeOnSystem(String timeOnSystem) {
        this.timeOnSystem = timeOnSystem;
    }
}

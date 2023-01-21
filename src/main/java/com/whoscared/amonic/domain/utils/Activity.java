package com.whoscared.amonic.domain.utils;

import com.whoscared.amonic.domain.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @Column(name = "date")
    private String date;
    @Column(name = "login_time")
    private Long loginTime;
    @Column(name = "logout_time")
    private Long logoutTime;
    @Column(name = "time_spend_on_system")
    private Time timeSpendOnSystem;
    @Column(name = "unsuccessful_logout_reason")
    private UnsuccessfulLogoutReason unsuccessfulLogoutReason;


    public void setDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        this.date = simpleDateFormat.format(date);
    }

    public Date getLoginTime() {
        return new Date(loginTime);
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime.getTime();
    }

    public Date getLogoutTime() {
        return new Date(logoutTime);
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime.getTime();
    }

}

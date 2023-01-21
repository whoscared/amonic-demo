package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.security.PersonDetails;
import com.whoscared.amonic.services.ActivityService;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;
    private final ActivityService activityService;

    @Autowired
    public UserController(PersonService personService, ActivityService activityService) {
        this.personService = personService;
        this.activityService = activityService;
    }

    @GetMapping("/main")
    public String main(Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personService.findByEmail(personDetails.getUsername());
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        model.addAttribute("person", person);
        Time activity = activityService.getTimeSpendOnSystemLast30days(person);
        if (activity != null) {
            model.addAttribute("time", activityService.getTimeSpendOnSystemLast30days(person));
        } else {
            model.addAttribute("time", "00:00:00");
        }
        model.addAttribute("count", activityService.getCountUnsuccessfulLogout(person));
        return "user/main";
    }
}

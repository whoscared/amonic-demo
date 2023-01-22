package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.domain.utils.UnsuccessfulLogoutReason;
import com.whoscared.amonic.security.PersonDetails;
import com.whoscared.amonic.services.ActivityService;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unsuccessful_logout_reason")
public class UnsuccessfulLogoutReasonController {

    private final PersonService personService;
    private final ActivityService activityService;

    @Autowired
    public UnsuccessfulLogoutReasonController(PersonService personService, ActivityService activityService) {
        this.personService = personService;
        this.activityService = activityService;
    }

    @GetMapping
    public String unsuccessfulLogoutReason() {
        return "message/unsuccessful_logout_reason";
    }

    @PostMapping
    public String getUnsuccessfulLogoutReason(@ModelAttribute("reasonText") String text,
                                              @ModelAttribute("reason") UnsuccessfulLogoutReason reason) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personService.findByEmail(personDetails.getUsername());
        Activity current = activityService.getLastActivityByPerson(person);
        activityService.setUnsuccessfulLogoutReason(current, reason);
        return "user/main";
    }

}

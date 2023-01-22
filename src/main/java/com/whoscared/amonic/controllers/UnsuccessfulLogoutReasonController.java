package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.person.TypeOfRole;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.domain.utils.UnsuccessfulLogout;
import com.whoscared.amonic.domain.utils.UnsuccessfulLogoutReason;
import com.whoscared.amonic.security.PersonDetails;
import com.whoscared.amonic.services.ActivityService;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unsuccessful_logout")
public class UnsuccessfulLogoutReasonController {

    private final PersonService personService;
    private final ActivityService activityService;

    @Autowired
    public UnsuccessfulLogoutReasonController(PersonService personService, ActivityService activityService) {
        this.personService = personService;
        this.activityService = activityService;
    }

    @GetMapping()
    public String unsuccessfulLogoutReason(Model model) {
        model.addAttribute("unsuccLog", new UnsuccessfulLogout());
        return "message/unsuccessful_logout_reason";
    }

    @PostMapping()
    public String getUnsuccessfulLogoutReason(@ModelAttribute("unsuccLog") UnsuccessfulLogout unsuccessfulLogout) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personService.findByEmail(personDetails.getUsername());
        Activity current = activityService.getLastActivityByPerson(person);
        activityService.setUnsuccessfulLogoutReason(current, unsuccessfulLogout.getReason());
        if (person.getRole().equals(TypeOfRole.ROLE_USER)){
            return "redirect:/user/main";
        }
        else {
            return "redirect:/admin/main";
        }
    }

}

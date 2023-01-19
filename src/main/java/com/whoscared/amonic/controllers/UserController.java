package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.security.PersonDetails;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public Person main (){
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personService.findByEmail(personDetails.getUsername());
        if (person == null){
            throw new UsernameNotFoundException("User not found");
        }
        return person;
    }
}

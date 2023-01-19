package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.person.TypeOfAccess;
import com.whoscared.amonic.security.PersonValidator;
import com.whoscared.amonic.services.OfficeService;
import com.whoscared.amonic.services.PersonService;
import com.whoscared.amonic.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final OfficeService officeService;
    private final RoleService roleService;

    private final PersonValidator personValidator;
    private final PersonService personService;

    @Autowired
    public AuthController(OfficeService officeService, RoleService roleService, PersonValidator personValidator, PersonService personService) {
        this.officeService = officeService;
        this.roleService = roleService;
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person,
                                   Model model) {
        model.addAttribute("officeList", officeService.findAll());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("person") @Valid Person person,
                             Model model,
                             BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("officeList", officeService.findAll());
            return "auth/registration";
        }

        person.setRole(roleService.user());
        person.setAccess(TypeOfAccess.NOT_BLOCKED);
        personService.register(person);
        return "redirect:/auth/login";
    }


}

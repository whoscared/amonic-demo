package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.person.TypeOfAccess;
import com.whoscared.amonic.domain.person.TypeOfRole;
import com.whoscared.amonic.domain.utils.Office;
import com.whoscared.amonic.security.PersonValidator;
import com.whoscared.amonic.services.ActivityService;
import com.whoscared.amonic.services.OfficeService;
import com.whoscared.amonic.services.PersonService;
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
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;
    private final OfficeService officeService;
    private final PersonValidator personValidator;


    @Autowired
    public AdminController(PersonService personService,
                           OfficeService officeService, PersonValidator personValidator) {
        this.personService = personService;
        this.officeService = officeService;
        this.personValidator = personValidator;
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("office", new Office());
        model.addAttribute("personList", personService.findAll());
        model.addAttribute("officeList", officeService.findAll());
        return "admin/main";
    }

    @PostMapping("/main")
    public String chooseOffice(@ModelAttribute("office") Office office,
                               Model model) {

        model.addAttribute("personList", personService.findByOffice(officeService.findById(office.getId())));
        model.addAttribute("office", new Office());
        model.addAttribute("officeList", officeService.findAll());

        return "admin/main";
    }

    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("officeList", officeService.findAll());
        return "admin/new_user";
    }

    @PostMapping("/add_user")
    public String saveUser(@ModelAttribute("person") @Valid Person person,
                           BindingResult bindingResult, Model model) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("officeList", officeService.findAll());
            return "admin/new_user";
        }
        person.setRole(TypeOfRole.ROLE_USER);
        person.setAccess(TypeOfAccess.NOT_BLOCKED);
        personService.register(person);
        return "redirect:/admin/main";
    }
}

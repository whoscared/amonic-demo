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
import org.springframework.web.bind.annotation.*;

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
            //model.addAttribute("officeList", officeService.findAll());
            return "redirect:/admin/new_user";
        }
        person.setRole(TypeOfRole.ROLE_USER);
        person.setAccess(TypeOfAccess.NOT_BLOCKED);
        personService.register(person);
        return "redirect:/admin/main";
    }

    @GetMapping("/change_role")
    public String changeRole(@ModelAttribute("error") String error,
                             Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("officeList", officeService.findAll());
        return "admin/change_role";
    }

    @PostMapping("/change_role")
    public String changeRolePost (@ModelAttribute("person") @Valid Person person,
                BindingResult bindingResult, Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("officeList", officeService.findAll());
        if (bindingResult.hasErrors()) {
            return "admin/change_role";
        }
        Person current =  personService.findByEmail(person.getEmail());
        if (current == null){
            model.addAttribute("error", "User with this email not found");
            return "admin/change_role";
        }
        else {
            boolean match = person.getFirstname().equals(current.getFirstname())
                    && person.getLastname().equals(current.getLastname())
                    && person.getOffice().getId().equals(current.getOffice().getId());
            if (match) {
                current.setRole(person.getRole());
                personService.save(current);
            }
            else {
                model.addAttribute("error", "User data incorrect");
                return "admin/change_role";
            }
        }

        return "redirect:/admin/main";

    }
}

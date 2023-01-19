package com.whoscared.amonic.controllers;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Office;
import com.whoscared.amonic.services.ActivityService;
import com.whoscared.amonic.services.OfficeService;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;
    private final OfficeService officeService;
    private final ActivityService activityService;


    @Autowired
    public AdminController(PersonService personService, OfficeService officeService, ActivityService activityService) {
        this.personService = personService;
        this.officeService = officeService;
        this.activityService = activityService;
    }

    @GetMapping()
    public String main (Model model){
        model.addAttribute("office", new Office());
        model.addAttribute("personList",personService.findAll());
        model.addAttribute("officeList",officeService.findAll());
        return "admin/main";
    }

    @PostMapping()
    public String chooseOffice (@ModelAttribute ("office") Office office,
                                Model model){
        model.addAttribute("personList", personService.findByOffice(office));
        model.addAttribute("office", new Office());
        model.addAttribute("officeList",officeService.findAll());

        return "admin/main";
    }

    @GetMapping("/add_user")
    public String addUser(Model model){
        model.addAttribute("person",new Person());
        model.addAttribute("officeList",officeService.findAll());
        return "admin/add_user";
    }

    @PostMapping("/add_user")
    public String saveUser (@ModelAttribute("person") Person person){
        personService.save(person);
        return "admin/main";
    }
}

package com.whoscared.amonic.security;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.findByEmail(person.getEmail()) == null) {
            return;
        }
        errors.rejectValue("email", "", "Person with this email already exist");
    }
}

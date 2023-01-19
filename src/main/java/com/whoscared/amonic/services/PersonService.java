package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Office;
import com.whoscared.amonic.repositories.PersonRepository;
import com.whoscared.amonic.security.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, Md5PasswordEncoder md5PasswordEncoder) {
        this.personRepository = personRepository;
        this.md5PasswordEncoder = md5PasswordEncoder;
    }

    public Person findByEmail (String email){
        return personRepository.findByEmail(email).orElse(null);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public void save (Person person){
        personRepository.save(person);
    }

    public List<Person> findByOffice (Office office){
        return personRepository.findAllByOffice(office);
    }

    public void register (Person person){
        person.setPassword(md5PasswordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}

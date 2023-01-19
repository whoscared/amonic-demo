package com.whoscared.amonic.security;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.repositories.ActivityRepository;
import com.whoscared.amonic.repositories.PersonRepository;
import com.whoscared.amonic.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final PersonRepository personRepository;
    private final ActivityRepository activityRepository;
    private final PersonDetailService personDetailService;

    @Autowired
    public AuthProviderImpl(PersonRepository personRepository, ActivityRepository activityRepository, PersonDetailService personDetailService) {
        this.personRepository = personRepository;
        this.activityRepository = activityRepository;
        this.personDetailService = personDetailService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        Person user = personRepository.findByEmail(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        UserDetails personDetails = personDetailService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password!hey");
        }

        Activity tempActivity = new Activity();
        tempActivity.setPerson(user);
        tempActivity.setDate(new Date());
        tempActivity.setLoginTime(new Date());
        activityRepository.save(tempActivity);

        return new UsernamePasswordAuthenticationToken(personDetails, password, personDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

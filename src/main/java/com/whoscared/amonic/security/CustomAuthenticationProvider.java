package com.whoscared.amonic.security;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.repositories.ActivityRepository;
import com.whoscared.amonic.repositories.PersonRepository;
import com.whoscared.amonic.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
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
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PersonRepository personRepository;
    private final ActivityRepository activityRepository;
    private final PersonDetailService personDetailService;

    private final Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    public CustomAuthenticationProvider(PersonRepository personRepository, ActivityRepository activityRepository, PersonDetailService personDetailService, Md5PasswordEncoder md5PasswordEncoder) {
        this.personRepository = personRepository;
        this.activityRepository = activityRepository;
        this.personDetailService = personDetailService;
        this.md5PasswordEncoder = md5PasswordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        Person user = personRepository.findByEmail(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        UserDetails personDetails = personDetailService.loadUserByUsername(username);

        if (!personDetails.isAccountNonLocked()){
            throw new AccountExpiredException("You are locked!");
        }

        String password = authentication.getCredentials().toString();
        if (!md5PasswordEncoder.matches(password, personDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password!");
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

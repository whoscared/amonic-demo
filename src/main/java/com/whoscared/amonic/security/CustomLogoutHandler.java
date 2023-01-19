package com.whoscared.amonic.security;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.repositories.PersonRepository;
import com.whoscared.amonic.services.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final PersonRepository personRepository;
    private final ActivityService activityService;

    @Autowired
    public CustomLogoutHandler(PersonRepository personRepository, ActivityService activityService) {
        this.personRepository = personRepository;
        this.activityService = activityService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Person user = personRepository.findByEmail(authentication.getName()).orElse(null);
        Activity currentActivity = activityService.getLastActivityByPerson(user);
        activityService.successfulLogout(currentActivity.getId(), new Date());
    }
}

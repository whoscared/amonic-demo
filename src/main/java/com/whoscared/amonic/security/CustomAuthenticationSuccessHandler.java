package com.whoscared.amonic.security;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.person.TypeOfRole;
import com.whoscared.amonic.services.PersonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler userSuccessUrl = new SimpleUrlAuthenticationSuccessHandler("/user/main");
    SimpleUrlAuthenticationSuccessHandler adminSuccessUrl = new SimpleUrlAuthenticationSuccessHandler("/admin/main");

    private final PersonService personService;

    @Autowired
    public CustomAuthenticationSuccessHandler(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        Person user = personService.findByEmail(username);
        if (user.getRole().equals(TypeOfRole.ROLE_ADMIN)) {
            this.adminSuccessUrl.onAuthenticationSuccess(request, response, authentication);
        }
        this.userSuccessUrl.onAuthenticationSuccess(request, response, authentication);
    }
}

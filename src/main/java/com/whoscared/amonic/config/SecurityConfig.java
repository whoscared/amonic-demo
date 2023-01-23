package com.whoscared.amonic.config;

import com.whoscared.amonic.security.CustomAuthenticationProvider;
import com.whoscared.amonic.security.CustomAuthenticationSuccessHandler;
import com.whoscared.amonic.security.CustomLogoutHandler;
import com.whoscared.amonic.security.Md5PasswordEncoder;
import com.whoscared.amonic.services.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationProvider authProvider;
    private final CustomLogoutHandler customLogoutHandler;

    private final CustomAuthenticationSuccessHandler successHandler;
    private final PersonDetailService personDetailService;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider authProvider,
                          CustomLogoutHandler customLogoutHandler, CustomAuthenticationSuccessHandler successHandler, PersonDetailService personDetailService) {
        this.authProvider = authProvider;
        this.customLogoutHandler = customLogoutHandler;
        this.successHandler = successHandler;
        this.personDetailService = personDetailService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //TODO: watch lesson about csrf
        http.csrf().disable();
        http.authenticationProvider(authProvider);
        http.userDetailsService(personDetailService);
        http
                .authorizeRequests(auth ->
                {
                    auth.requestMatchers("/auth/login", "/auth/registration", "/error").permitAll();
                    auth.requestMatchers("/main", "/unsuccessful_logout_reason").authenticated();
                    auth.requestMatchers("/admin/*", "/schedules_changes", "/schedule/*", "/flight/*","/manage_flight_schedules").hasRole("ADMIN");
                    auth.requestMatchers("/user/*").hasRole("USER");
                });

        http.formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .successHandler(successHandler)
                .failureUrl("/auth/login?error");
        http
                .logout().addLogoutHandler(customLogoutHandler);
        return http.build();

    }

//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authPr = new DaoAuthenticationProvider();
//        authPr.setUserDetailsService(personDetailService);
//        authPr.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        return authPr;
//    }
}

package com.whoscared.amonic.config;

import com.whoscared.amonic.security.AuthProviderImpl;
import com.whoscared.amonic.security.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthProviderImpl authProvider;
    private final CustomLogoutHandler customLogoutHandler;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider,
                          CustomLogoutHandler customLogoutHandler) {
        this.authProvider = authProvider;
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider)
                .authorizeRequests(auth ->
                {
                    auth.requestMatchers("/auth/login", "/auth/registration", "/error").permitAll();
                    auth.requestMatchers("/admin/*").hasRole("ADMIN");
                    auth.requestMatchers("/user/*").hasRole("USER");
                });
        http.formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/auth/process_login")
                .defaultSuccessUrl("/main", true)
                .failureUrl("/auth/login");
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

package com.whoscared.amonic.config;

import com.whoscared.amonic.security.AuthProviderImpl;
import com.whoscared.amonic.security.CustomLogoutHandler;
import com.whoscared.amonic.security.Md5PasswordEncoder;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public PasswordEncoder getPasswordEncoder(){
        return new Md5PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authenticationProvider(authProvider);
        http
                .authorizeRequests(auth ->
                {
                    auth.requestMatchers("/auth/login", "/auth/registration", "/error").permitAll();
                    auth.requestMatchers("/main").authenticated();
                    auth.requestMatchers("/admin/*").hasRole("ADMIN");
                    auth.requestMatchers("/user/*").hasRole("USER");
                });

        http.formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/main", true)
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

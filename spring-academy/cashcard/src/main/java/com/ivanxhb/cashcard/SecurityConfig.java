package com.ivanxhb.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
// What does the Configuration annotation do?
// Tells Spring to use this class to configure Spring and Spring Boot itself.
// Any beans specified in thi class will now be available to Spring's Auto
// Configuration engine
public class SecurityConfig {

    @Bean // What does the bean annotation do?
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Step 5: Configure Basic Authentication


        /*
         All HTTP request to cashcard/ endpoints are required to be authenticated
         using HTTP Basic Authentication security(username and password)
         and CSRF is not required
         */
        http.authorizeHttpRequests()
        .requestMatchers("/cashcards/**")
        .authenticated()
        .and()
        .csrf().disable()
        .httpBasic();

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {

        // Springs IoC will find the UserDetailsService Bean
        // Spring Data will use it when needed
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
        .username("sarah1")
        .password(passwordEncoder.encode("abc123"))
        .roles() // No roles for now
        .build();
        return new InMemoryUserDetailsManager(sarah);
    }

}
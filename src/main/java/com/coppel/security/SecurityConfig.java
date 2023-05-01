package com.coppel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author luis.buelna
 */

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .httpBasic()
                .and().authorizeHttpRequests()
                .regexMatchers("/public/*").permitAll()
                .regexMatchers("/admin/*").hasAnyRole("ADMIN")
                .and().build();
    }
    
    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .httpBasic()
                .and().authorizeHttpRequests(
                        authorize -> authorize
                    //      .anyRequest().permitAll()
                    //      .anyRequest().denyAll()
                    //      .anyRequest().authenticated
                    //      .anyRequest().hasRole("ADMIN")
                            .anyRequest().hasAuthority("read")
                )
                .build();
    }*/
    
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                    .password(passwordEncoder().encode("user"))
                    .authorities("ROLE_USER")
                    .build(),
                User.withUsername("admin")
                    .password(passwordEncoder().encode("admin")) 
                    .authorities("ROLE_ADMIN")
                    .build()
        );
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
}

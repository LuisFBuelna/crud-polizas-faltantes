package com.coppel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * CLASE QUE PERMITE CONFIGURAR LOS PERMISOS Y AUTENTICACION DESEADA
 * @author luis.buelna
 */
/*
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeRequests()
                .antMatchers("/publico/**").permitAll()         //TODAS LAS URL QUE COMIENCEN CON PUBLICO SE DA ACCESO
                .antMatchers("/admin/**").hasRole("ADMIN")      // Todas las url que comiencen con adcmin, solo se permite el acceso al usuario con rol Admin
                .anyRequest().authenticated();
        return http.build();
    }
    
    @Bean
    protected UserDetailsService userDetailsService(){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("walmart")
                    .password(passwordEncoder.encode("walmart"))
                    .roles("USER")
                    .authorities("read")
                    .build(),
                User.withUsername("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("ADMIN")
                    .authorities("read", "write", "")
                    .build()
        );
    }
    /*
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("walmart").password("walmart").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }*/
    

    


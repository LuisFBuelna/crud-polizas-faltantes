package com.coppel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .antMatchers("/publico/**").permitAll() //TODAS LAS URL QUE COMIENCEN CON PUBLICO SE DA ACCESO
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("walmart").password("walmart").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }
    
    @Bean
}
*/
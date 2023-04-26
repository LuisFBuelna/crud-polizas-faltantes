package com.coppel.controllers;

import org.slf4j.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("admin/hello")
@RestController
public class HelloWorldController {
    
    private static final Logger logger = (Logger) LoggerFactory.getLogger(HelloWorldController.class);
    
    @GetMapping
    public String helloWorld(@RequestParam(value="name", defaultValue="World") String name ){
        
        var auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Permisos: {}", auth.getAuthorities());
        logger.info("Esta autenticado?: {}", auth.isAuthenticated());
        
        return "Hello " + name + "!!";
    }
}

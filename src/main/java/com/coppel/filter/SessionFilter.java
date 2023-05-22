package com.coppel.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author luis.buelna
 */
@Component
@Order(1)
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Aloja");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final RestTemplate client = new RestTemplate();
        final HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add(HttpHeaders.AUTHORIZATION, req.getHeader(HttpHeaders.AUTHORIZATION));
        final HttpEntity<String> httpEntity = new HttpEntity<>(authHeaders);
        try {
            final ResponseEntity<String> authResponse = client.exchange("https://authentication-dev.coppel.io:8443/api/sso/v1/verify", HttpMethod.POST, httpEntity, String.class);
            if (authResponse.getStatusCode() == HttpStatus.OK) {
                chain.doFilter(request, response);
            }
        } catch (RestClientException ex) {
        }

    }

}

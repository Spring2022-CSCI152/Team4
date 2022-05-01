package com.CSCI152.team4.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();/*Allow local requests for testing*/
        http.headers().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedMethod(CorsConfiguration.ALL);
                config.addAllowedMethod(HttpMethod.PUT);
                config.addAllowedMethod(HttpMethod.POST);
                config.addAllowedMethod(HttpMethod.GET);
                config.addAllowedOrigin(CorsConfiguration.ALL);
                return config;
            }
        });
    }


}
package com.ailiens.common.config;

import com.ailiens.common.security.CustomAuthenticationEntryPoint;
import com.ailiens.common.security.CustomAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public abstract class WebSecurityConfig {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    protected CustomAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);

    }
}

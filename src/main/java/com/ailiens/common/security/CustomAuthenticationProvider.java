package com.ailiens.common.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
        List<String> roleList = new ArrayList<>(Arrays.asList(customAuthentication.getRoles().split("\\s*,\\s*")));
        List<GrantedAuthority> authorities = roleList.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        return new CustomAuthentication(customAuthentication.getTenantId(), customAuthentication.getUserId(), customAuthentication.getFcId(), customAuthentication.getRoles(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (CustomAuthentication.class.isAssignableFrom(authentication));
    }
}

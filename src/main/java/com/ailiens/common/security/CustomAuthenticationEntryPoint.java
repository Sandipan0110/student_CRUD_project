package com.ailiens.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ailiens.common.constants.Constants.MESSAGE_UNAUTHORIZED;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
        throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), MESSAGE_UNAUTHORIZED);
    }
}

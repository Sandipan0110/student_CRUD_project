package com.ailiens.common.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ailiens.common.constants.Constants.DEFAULT_LOGIN_URL;
import static com.ailiens.common.constants.Constants.EMPTY_ROLES;
import static com.ailiens.common.constants.Constants.HEADER_FC_ID;
import static com.ailiens.common.constants.Constants.HEADER_ROLES;
import static com.ailiens.common.constants.Constants.HEADER_TENANT_ID;
import static com.ailiens.common.constants.Constants.HEADER_USER_ID;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {


    private static final RequestMatcher DEFAULT_REQUEST_MATCHER =
        new OrRequestMatcher(new AntPathRequestMatcher(DEFAULT_LOGIN_URL), new RequestHeaderRequestMatcher(HEADER_ROLES));


    public CustomAuthenticationProcessingFilter() {
        super(DEFAULT_REQUEST_MATCHER);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        String tenantId = request.getHeader(HEADER_TENANT_ID);
        String userId = request.getHeader(HEADER_USER_ID);
        String fcId = request.getHeader(HEADER_FC_ID);
        String roles = request.getHeader(HEADER_ROLES);
        if (StringUtils.isEmpty(roles)) {
            throw new AuthenticationServiceException(EMPTY_ROLES);
        }
        return getAuthenticationManager().authenticate(new CustomAuthentication(tenantId, userId, fcId, roles));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}

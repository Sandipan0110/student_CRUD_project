package com.ailiens.common.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomAuthentication extends AbstractAuthenticationToken {


    String tenantId;
    String userId;
    String fcId;
    String roles;

    public CustomAuthentication(String tenantId, String userId, String fcId, String roles, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
        this.tenantId = tenantId;
        this.userId = userId;
        this.fcId = fcId;
        this.roles = roles;
    }

    public CustomAuthentication(String tenantId, String userId, String fcId, String roles) {
        super(null);
        this.tenantId = tenantId;
        this.userId = userId;
        this.fcId = fcId;
        this.roles = roles;
    }

    @Override
    public Object getCredentials() {
        return roles;
    }

    @Override
    public String getPrincipal() {
        return userId;
    }

}

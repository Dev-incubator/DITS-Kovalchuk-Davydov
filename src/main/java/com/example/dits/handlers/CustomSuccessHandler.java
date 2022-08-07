package com.example.dits.handlers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Getter
@Setter
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @NonNull
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) {
            System.err.println("Can't redirect");
            return;
        }
            redirectStrategy.sendRedirect(request,response,targetUrl);

        super.handle(request, response, authentication);
    }

    private String determineTargetUrl(Authentication authentication){
        String url ;
        Collection<? extends GrantedAuthority> authorities =
                                                authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        addAuthoritiesToRoles(authorities, roles);
        url = determinateURL(roles);
        return url;
    }

    private String determinateURL(List<String> roles) {
        String url;
        if (isUser(roles) || isAdmin(roles))
            url = "/login-handle";
        else
            url = "/accessDenied";
        return url;
    }

    private void addAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities, List<String> roles) {
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
    }

    private boolean isUser(List<String> roles){
        return roles.contains("ROLE_USER");
    }

    private boolean isAdmin(List<String> roles){
        return roles.contains("ROLE_ADMIN");
    }
}

package csci5408.catme.authentication;

import csci5408.catme.dto.UserSummary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

import static csci5408.catme.authentication.AuthConfig.AUTH_COOKIE_NAME;

public class SessionFilter extends GenericFilter {

    ISessionStore sessionStore;

    public SessionFilter(ISessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if(cookieName.equals(AUTH_COOKIE_NAME)) {
                    UserSummary userSummary = sessionStore.getSession(cookie.getValue());
                    if(userSummary != null) {
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(
                                        new UsernamePasswordAuthenticationToken(
                                                userSummary, "", new ArrayList<>()
                                        )
                                );
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}

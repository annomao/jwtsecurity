package com.security.jwtsecurity.security;

import com.security.jwtsecurity.services.AppUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AppUserDetailsService appUserDetailsService;

    public AuthTokenFilter(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            var token = headerValue.substring(7);
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            try {
                var name= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
                var user = appUserDetailsService.loadUserByUsername(name);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, null, user.getAuthorities());
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(securityContext);
            }catch (Exception e){
                response.setStatus(401);
                response.getWriter().println("Access denied");
                return;
            }

        }
        filterChain.doFilter(request,response);
    }
}

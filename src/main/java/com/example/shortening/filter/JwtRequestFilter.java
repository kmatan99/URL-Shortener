package com.example.shortening.filter;

import com.example.shortening.model.UserEntity;
import com.example.shortening.repository.UserRepository;
import com.example.shortening.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.management.relation.Role;
import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final static String AUTHORIZATION = "authorization";

    private final static String BEARER_PREFIX = "Bearer ";

    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        final String requestToken = request.getHeader(AUTHORIZATION);

        if (requestToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = removeBearerIfExisting(requestToken);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

        if (usernameExistsAndNotLoggedIn(username)) {
            UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                    new EntityNotFoundException("User by username: " + username + "doesn't exist"));

            User user = new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());

            if (jwtTokenUtil.isTokenValid(jwtToken)) {
                //tell spring security user is authenticated
                setSpringSecurityAuth(request, user);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setSpringSecurityAuth(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String removeBearerIfExisting(String requestToken) {
        return requestToken.startsWith(BEARER_PREFIX) ? requestToken.substring(7) : requestToken;
    }

    private boolean usernameExistsAndNotLoggedIn(String username) {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }
}

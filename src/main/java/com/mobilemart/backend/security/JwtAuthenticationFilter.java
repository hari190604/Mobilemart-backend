package com.mobilemart.backend.security;

import com.mobilemart.backend.entity.JwtToken;
import com.mobilemart.backend.repository.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);
            if (jwt != null) {
                System.out.println("DEBUG JWT FILTER: parsed jwt: " + jwt.substring(0, Math.min(20, jwt.length())) + "...");
                // Check if token exists in database (meaning it hasn't been invalidated/logged out)
                Optional<JwtToken> tokenOpt = jwtTokenRepository.findByToken(jwt);
                System.out.println("DEBUG JWT FILTER: token present in DB: " + tokenOpt.isPresent());
                
                if (tokenOpt.isPresent()) {
                    String username = jwtUtil.extractUsername(jwt);
                    System.out.println("DEBUG JWT FILTER: username in token: " + username);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    System.out.println("DEBUG JWT FILTER: loaded user Details: " + userDetails.getUsername() + ", enabled: " + userDetails.isEnabled());

                    if (jwtUtil.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("DEBUG JWT FILTER: Auth Successful with authorities: " + userDetails.getAuthorities());
                    } else {
                        System.out.println("DEBUG JWT FILTER: JwtUtil validateToken returned false!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

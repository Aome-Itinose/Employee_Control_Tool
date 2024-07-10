package org.aome.employee_control_tool.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.util.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.util.responses.ExceptionResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final UserSecurityDetailsService userSecurityDetailsService;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    private HttpServletRequest request;
    private String token;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authentication");
            this.request = request;

            if (headerIsValid(authHeader)) {
                token = authHeader.substring(7);
                checkJWTAndSetAuthentication();
            }

        } catch (UserNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(e.getMessage(), LocalDateTime.now())));
        }catch (JWTVerificationException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse("Jwt is incorrect", LocalDateTime.now())));
        }
        if(response.getStatus() != HttpServletResponse.SC_BAD_REQUEST)
            filterChain.doFilter(request, response);
    }

    private void checkJWTAndSetAuthentication(){
        String username = validateTokenAndRetrieveUsername(token);
        UserDetails userDetails = userSecurityDetailsService.loadUserByUsername(username);
        setSecurityContextIfItEmpty(userDetails);
    }
    private boolean headerIsValid(String authHeader) {
        return authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ");
    }
    private String validateTokenAndRetrieveUsername(String token) {
        if (token.isBlank()) {
            throw new JWTVerificationException("Token is empty");
        }
        return jwtUtil.validateTokenAndRetrievedSubject(token);
    }
    private void setSecurityContextIfItEmpty(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}

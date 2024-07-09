package org.aome.employee_control_tool.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aome.employee_control_tool.security.JWTUtil;
import org.aome.employee_control_tool.security.UserSecurityDetails;
import org.aome.employee_control_tool.security.UserSecurityDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final UserSecurityDetailsService userSecurityDetailsService;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = validateAuthenticationHeaderAndReturnToken(request);
            UserDetails userDetails = validateTokenAndRetrieveUserDetails(token);

            setSecurityContextIfItEmpty(userDetails);
        } catch (JWTVerificationException e) {
            /*Todo: Do something with this filter exceptions*/
        }/*catch(UserNotFoundException e){

        }catch(AuthenticationHeaderInvalidException e){

        }*/
        filterChain.doFilter(request, response);
    }

    private String validateAuthenticationHeaderAndReturnToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authentication");
        if(authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
            /*Todo: AuthenticationHeaderInvalidException*/
            return "";
        }
        return authHeader.substring(7);
    }
    private UserSecurityDetails validateTokenAndRetrieveUserDetails(String token){
        if(token.isBlank()){
            throw new JWTVerificationException("Token is empty");
        }
        String username = jwtUtil.validateTokenAndRetrievedSubject(token);
        return userSecurityDetailsService.loadUserByUsername(username);
    }
    private void setSecurityContextIfItEmpty(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}

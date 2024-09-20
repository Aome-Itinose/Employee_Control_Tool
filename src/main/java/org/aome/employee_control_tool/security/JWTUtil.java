package org.aome.employee_control_tool.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${spring.security.jwt.private-key}")
    private String privateKey;

    @Value("${spring.security.jwt.issuer}")
    private String issuer;
    
    @Value("${spring.security.jwt.expiration-minutes}")
    private Integer expirationMinutes;

    public String generateToken(String username){
        ZonedDateTime expirationAt = ZonedDateTime.now().plusMinutes(expirationMinutes);
        Date expirationDate = Date.from(expirationAt.toInstant());
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(privateKey));
    }

    public String validateTokenAndRetrievedSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey))
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

}

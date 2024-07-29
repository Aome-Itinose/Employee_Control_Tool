package org.aome.employee_control_tool.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.private-key}")
    private String privateKey;

    @Value("${security.jwt.issuer}")
    private String issuer;
    
    @Value("${security.jwt.expiration-minutes}")
    private Integer expirationMinutes;

    public String generateToken(String username){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant());
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(privateKey));
    }

    public String validateTokenAndRetrievedSubject(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(privateKey))
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

}

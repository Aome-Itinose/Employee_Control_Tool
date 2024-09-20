package org.aome.employee_control_tool.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JWTUtilTest {
    @InjectMocks
    private JWTUtil jwtUtil;


    private final Integer expirationMinutes = 60;
    private final String privateKey = "test";
    private final String issuer = "test";

    private final String username = "testUsername";
    private final String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0VXNlcm5hbWUiLCJpYXQiOjE3MDQwNTY0MDAsImlzcyI6InRlc3QiLCJleHAiOjE3MDQwNjAwMDB9.dpCQ9alSCn-YdDvtP6aDH46kGZcOoYv8sOZx0lgjn3k";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "issuer", issuer);
        ReflectionTestUtils.setField(jwtUtil, "privateKey", privateKey);
        ReflectionTestUtils.setField(jwtUtil, "expirationMinutes", expirationMinutes);
    }

    @Test
    void generateToken() {
        ZonedDateTime now = ZonedDateTime.of(2024, 1,1,0,0,0,0, ZoneId.of("Europe/Moscow"));
        ZonedDateTime expirationAt = now.plusMinutes(expirationMinutes);

        try(MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = Mockito.mockStatic(ZonedDateTime.class)) {
            zonedDateTimeMockedStatic.when(ZonedDateTime::now).thenReturn(now);
            given(now.plusMinutes(expirationMinutes)).willReturn(expirationAt);

            assertEquals(now, ZonedDateTime.now());
            String actualJwt = jwtUtil.generateToken(username);
            assertEquals(jwt, actualJwt);
        }
    }

    @Test
    void validateTokenAndRetrievedSubject_tokenExpiredException() {
        assertThrows(TokenExpiredException.class, () -> jwtUtil.validateTokenAndRetrievedSubject(jwt));
    }

    @Test
    void validateTokenAndRetrievedSubject_validToken() {
        String token = jwtUtil.generateToken(username);
        assertEquals(username, jwtUtil.validateTokenAndRetrievedSubject(token));
    }
}
package org.aome.employee_control_tool.security;

import org.aome.employee_control_tool.exceptions.UserNotFoundException;
import org.aome.employee_control_tool.services.UserService;
import org.aome.employee_control_tool.store.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserSecurityDetailsServiceTest {
    @InjectMocks
    private UserSecurityDetailsService userSecurityDetailsService;

    @Mock
    private UserService userService;

    private final String username = "TestUsername";

    @Test
    void loadUserByUsername_returnUserDetails() {
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password("Test")
                .role("ROLE_TEST")
                .build();

        given(userService.findUserEntityByUsername(username)).willReturn(userEntity);

        UserDetails expected = new UserSecurityDetails(userEntity);
        UserDetails actual = userSecurityDetailsService.loadUserByUsername(username);
        assertEquals(expected, actual);
    }

    @Test
    void loadUserByUsername_throwsException() {
        given(userService.findUserEntityByUsername(username)).willThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userSecurityDetailsService.loadUserByUsername(username));
    }
}
package org.aome.employee_control_tool.util.responses;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends Response{
    private String token;

    public AuthenticationResponse(String token, String message, LocalDateTime time) {
        super(message, time);
        this.token = token;
    }
}


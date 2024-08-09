package org.aome.employee_control_tool.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends Response{
    @JsonProperty("token")
    private String token;

    public AuthenticationResponse(String token, String message, LocalDateTime time) {
        super(message, time);
        this.token = token;
    }
}


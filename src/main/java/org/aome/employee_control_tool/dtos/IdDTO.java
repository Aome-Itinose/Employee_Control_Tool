package org.aome.employee_control_tool.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class IdDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty("id")
    UUID id;
}

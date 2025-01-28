package org.kataolympp.model.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskInDto(@JsonProperty @NotBlank String label, @JsonProperty @NotNull Boolean completed) {
}


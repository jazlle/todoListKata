package org.kataolympp.model.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskInDto(@JsonProperty String label, @JsonProperty Boolean completed) {
}


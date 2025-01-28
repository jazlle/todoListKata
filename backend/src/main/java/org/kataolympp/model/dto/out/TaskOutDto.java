package org.kataolympp.model.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskOutDto(@JsonProperty Long id, @JsonProperty String label, @JsonProperty Boolean completed) {
}
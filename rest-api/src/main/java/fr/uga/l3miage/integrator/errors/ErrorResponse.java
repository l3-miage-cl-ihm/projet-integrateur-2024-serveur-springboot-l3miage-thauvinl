package fr.uga.l3miage.integrator.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ErrorResponse {
    @Schema(description = "end point call", example = "/api/")
    private final String uri;
}

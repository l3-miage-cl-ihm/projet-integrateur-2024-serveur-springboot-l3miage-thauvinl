package fr.uga.l3miage.integrator.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BadRequestErrorResponse {
    @Schema(
            description = "end point call",
            example = "/api/journees/create"
    )
    private final String uri;
    @Schema(
            description = "error message",
            example = "Failed to create journee"
    )
    private final String errorMessage;

}

package fr.uga.l3miage.integrator.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class NotFoundErrorResponse {
    @Schema(
            description = "end point call",
            example = "/api/journees/"
    )
    private final String uri;
    @Schema(
            description = "error message",
            example = "La journée J001 n'existe pas"
    )
    private final String errorMessage;

    NotFoundErrorResponse(final String uri, final String errorMessage) {
        this.uri = uri;
        this.errorMessage = errorMessage;
    }
}

package fr.uga.l3miage.integrator.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseStatusErrorResponse {
    @Schema(
            description = "end point call",
            example = "/api/tournees/"
    )
    private final String uri;
    @Schema(
            description = "error message",
            example = "Failed to create journee/tournee"
    )
    private final String errorMessage;
    ResponseStatusErrorResponse(final String uri, final String errorMessage) {
        this.uri = uri;
        this.errorMessage = errorMessage;
    }
}

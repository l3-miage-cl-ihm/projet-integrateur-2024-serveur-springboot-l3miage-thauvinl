package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des journées", description = "Tous les endpoints de gestion des journées")
@RestController
@RequestMapping("/api/journees")
public interface JourneeEndpoints {
    @Operation(description = "Création d'une journée")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "La journée a bien été créée"
    ),
    @ApiResponse(
            responseCode = "400",
            description = "Une erreur s'est produite avec la requête"
    )})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    JourneeResponseDTO createJournee(@RequestBody JourneeCreationRequest journeeCreationRequest);
}

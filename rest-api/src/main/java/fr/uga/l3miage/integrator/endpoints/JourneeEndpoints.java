package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.errors.AddJourneeErrorResponse;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des journées", description = "Tous les endpoints de gestion des journées")
@RestController
@RequestMapping("/api/journees")
public interface JourneeEndpoints {
    @Operation(description = "Création d'une journée")
    @ApiResponse(
            responseCode = "201",
            description = "La journée a bien été créée"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Une erreur s'est produite avec la requête"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    JourneeResponseDTO createJournee(@RequestBody JourneeCreationRequest journeeCreationRequest);

    @Operation(description = "récuperer une journée")
    @ApiResponses({@ApiResponse(responseCode = "200",
            description = "La journée a été trouvée"),
    @ApiResponse(responseCode ="404",
            description="La journée est introuvable",
            content = {@Content(schema = @Schema(implementation = NotFoundErrorResponse.class), mediaType = "application/json")})})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{idJournee}")
    JourneeResponseDTO getJournee(@PathVariable(name="idJournee")String idJournee);

    @Operation(description = "Ajouter une tournée à la journée")
    @ApiResponse(responseCode = "200",description = "La tournée a été ajoutée à la journée")
    @ApiResponse(responseCode = "404", description = "Une erreur s'est produite, la journée ou la tournée demandée n'a pas été trouvée",content = @Content(schema = @Schema(implementation = AddJourneeErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{idJournee}/add")
    JourneeResponseDTO addTourneeInJournee(@PathVariable(name = "idJournee")String idJournee, @RequestBody TourneeCreationRequest request);
}

package fr.uga.l3miage.integrator.endpoints;


import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.errors.BadRequestErrorResponse;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des journées", description = "Tous les endpoints de gestion des journées")
@RestController
@RequestMapping("/api/journees")
public interface JourneeEndpoints {
    @Operation(description = "Création d'une journée")
    @ApiResponse(responseCode = "201", description = "La journée a bien été créée")
    @ApiResponse(responseCode = "404", description = "Employé ou commande non trouvable", content = {@Content(schema = @Schema(implementation = NotFoundErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ApiResponse(responseCode = "400", description = "Une erreur s'est produite avec la requête", content = {@Content(schema = @Schema(implementation = BadRequestErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    JourneeResponseDTO createJournee(@RequestBody JourneeCreationRequest journeeCreationRequest);



    @Operation(description = "récuperer une journée")
    @ApiResponse(responseCode = "200", description = "La journée a été trouvée")
    @ApiResponse(responseCode = "404", description = "La journée est introuvable", content = {@Content(schema = @Schema(implementation = NotFoundErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{idJournee}")
    JourneeResponseDTO getJournee(@PathVariable(name = "idJournee") String idJournee);


}

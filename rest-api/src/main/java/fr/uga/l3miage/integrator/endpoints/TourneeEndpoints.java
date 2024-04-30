package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.errors.AddJourneeErrorResponse;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des tournées", description = "Tous les endpoints de gestion des tournées")
@RestController
@RequestMapping("/api/tournees")
public interface TourneeEndpoints {



    @Operation(description = "Accès à une tournée à partir du nom d'un de ses employés")
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            description = "La tournée réalisée par l'employé a été trouvée"),
            @ApiResponse(responseCode = "404",
                        description = "Aucune tournée n'est trouvée pour cet employé")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{employeId}")
    TourneeResponseDTO getTourneeByEmploye(@PathVariable(name = "employeId") String employeId);


    @Operation(description = "Ajouter une livraison à la tournée")
    @ApiResponse(responseCode = "200",description = "La livraison a été ajoutée à la tournée")
    @ApiResponse(responseCode = "404", description = "Une erreur s'est produite, la livraison ou la tournée demandée n'a pas été trouvée",content = @Content(schema = @Schema(implementation = AddJourneeErrorResponse.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{idTournee}/add")
    TourneeResponseDTO addLivraisonInTournee(@PathVariable(name = "idTournee")String idTournee, @RequestBody LivraisonCreationRequest request);
}

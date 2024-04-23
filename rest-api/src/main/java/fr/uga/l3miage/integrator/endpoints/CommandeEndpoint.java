package fr.uga.l3miage.integrator.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Gestion des livraisons", description = "Tous les endpoints des livraisons")
@RestController
@RequestMapping("/api/livraisons")
public interface CommandeEndpoint {

    @Operation(description = "Get toutes les commandes")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "Les commandes ont été récupérées"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getAllCommandes"
            )})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/AllCommandes")
    CommandeResponseDTO getAllCommandes();

    @Operation(description = "Get commande by reference")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "La commande a été récupérée par sa reference"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getCommandeByReference"
            )})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reference}")
    CommandeResponseDTO getCommandeByReference(@PathVariable String reference);

}
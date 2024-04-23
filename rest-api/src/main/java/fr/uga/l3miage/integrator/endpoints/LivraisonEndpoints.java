package fr.uga.l3miage.integrator.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
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
public interface LivraisonEndpoints {
    //mapping endpoint de getAllLivraisons
    @Operation(description = "Get toutes les livraisons")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "Les livraisons ont été récupérées"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getAllLivraisons"
            )})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/AllLivraisons")
    LivraisonResponseDTO getAllLivraisons();

    //mapiing endpoint de getLivraisonByReference
    @Operation(description = "Get livraison by reference")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "La livraisons a été récupérée par sa reference"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getLivraisonsByReference"
            )})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reference}")
    LivraisonResponseDTO getLivraisonByReference(@PathVariable String reference);

    //mapping endpoint de countLivraisons
    @Operation(description = "count toutes les livraisons")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "Les livraisons ont été compté"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de countLivraisons"
            )})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count")
    Long countLivraisons();

    @Operation(description = "get adresse from livraisons")
    @ApiResponses({@ApiResponse(
            responseCode = "201",
            description = "L'adresse a été récupérée"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getAdresseClientFromLivraison"
            )})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/adresseFromLivraison")
    AdresseResponseDTO getAdresseClientFromLivraison(@RequestBody String jsonData) throws JsonProcessingException ;




}
package fr.uga.l3miage.integrator.endpoints;


import fr.uga.l3miage.integrator.responses.ClientCommandesPairResponseDTO;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;


import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Set;


@Tag(name = "Gestion des commandes", description = "Tous les endpoints des commandes")
@RestController
@RequestMapping("/api/commandes")
public interface CommandeEndpoint {

    @Operation(description = "Get toutes les commandes")
    @ApiResponse(
            responseCode = "201",
            description = "Les commandes ont été récupérées"
    )
    @ApiResponse(
                    responseCode = "404",
                    description = "Une erreur s'est produite avec la requête de getAllCommandes"
            )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/AllCommandes")
    Set<CommandeResponseDTO> getAllCommandes();

    @Operation(description = "Get commande by reference")
    @ApiResponse(
            responseCode = "201",
            description = "La commande a été récupérée par sa reference"
    )
    @ApiResponse(
                    responseCode = "404",
                    description = "Une erreur s'est produite avec la requête de getCommandeByReference"
            )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reference}")
    CommandeResponseDTO getCommandeByReference(@PathVariable String reference);

    @Operation(description = "Get commandes group by client")
    @ApiResponse(
            responseCode = "201",
            description = "La commandes ont été récupérée "
    )
    @ApiResponse(
             responseCode = "404",
            description = "Une erreur s'est produite avec la requête de getCommandeGroupByClient"
            )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/CommandesByClient")
    Set<ClientCommandesPairResponseDTO> getCommandesGroupedByClient() ;
    @Operation(description = "update commande etat")
    @ApiResponse(
            responseCode = "201",
            description = "La commande a été update"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Une erreur s'est produite avec la requête de updateEtat de commande"
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateEtat/{reference}/{nvEtat}")
    CommandeResponseDTO updateEtat(@PathVariable String reference,@PathVariable String nvEtat);

    @Operation(description = "update la date de livraison effective d'une commande ainsi que la durée de livraison")
    @ApiResponse(
            responseCode = "200",
            description = "La commande a été update"
    )
    @ApiResponse(
            responseCode = "404",
            description = "La commande n'a pas été trouvée"
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateDate/{reference}")
    CommandeResponseDTO updateDateDeLivraison(@PathVariable String reference, @RequestParam String date);
}
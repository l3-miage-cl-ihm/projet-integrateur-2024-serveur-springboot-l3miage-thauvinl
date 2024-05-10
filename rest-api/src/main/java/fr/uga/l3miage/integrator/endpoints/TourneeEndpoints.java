package fr.uga.l3miage.integrator.endpoints;



import fr.uga.l3miage.integrator.errors.BadRequestErrorResponse;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des tournées", description = "Tous les endpoints de gestion des tournées")
@RestController
@RequestMapping("/api/tournees")
public interface TourneeEndpoints {
    @Operation(description = "Accès à une tournée à partir du nom d'un de ses employés")
    @ApiResponse(responseCode = "200", description = "La tournée réalisée par l'employé a été trouvée")
    @ApiResponse(responseCode = "404", description = "Aucune tournée n'est trouvée pour cet employé", content = {@Content(schema = @Schema(implementation = NotFoundErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{employeId}")
    TourneeResponseDTO getTourneeByEmploye(@PathVariable(name = "employeId" ) @Parameter(name = "employeId", description = "Trigramme de l'employé", example = "AAA") String employeId);

    @Operation(description = "Mettre à jour le temps de montage effectif d'une tournée")
    @ApiResponse(responseCode = "200", description = "Le temps de montage effectif a pu être mis à jour")
    @ApiResponse(responseCode = "404", description = "La tournée à mettre à jour est introuvable", content = {@Content(schema = @Schema(implementation = NotFoundErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("updateTdm/{reference}")
    TourneeResponseDTO updateTdmEffectifTournee(@PathVariable String reference, @RequestParam Integer tdmEffectif);

    @Operation(description = "update tournée etat")
    @ApiResponse(
            responseCode = "201",
            description = "La tournée a été update"
    )
    @ApiResponse(
            responseCode = "404",
            description = "La tournée n'a pas été trouvée"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Le tdm passé en paramètre n'est pas un entier naturel",
            content = {@Content(schema = @Schema(implementation = BadRequestErrorResponse.class))}
    )

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/updateEtat/{reference}")
    TourneeResponseDTO updateEtat(@PathVariable String reference, @RequestParam String nvEtat);
}

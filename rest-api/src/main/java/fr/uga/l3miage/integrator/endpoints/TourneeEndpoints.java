package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des tournées", description = "Tous les endpoints de gestion des tournées")
@RestController
@RequestMapping("/api/tournees")
public interface TourneeEndpoints {
    @Operation(description = "Accès à une tournée à partir du nom d'un de ses employés")
    @ApiResponse(responseCode = "200", description = "La tournée réalisée par l'employé a été trouvée")
    @ApiResponse(responseCode = "404", description = "Aucune tournée n'est trouvée pour cet employé")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{employeId}")
    TourneeResponseDTO getTourneeByEmploye(@PathVariable(name = "employeId") String employeId);
}

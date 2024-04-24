package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.requests.ClientCreationRequest;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Gestion des clients", description = "Tous les endpoints de gestion des clients")
@RestController
@RequestMapping("/api/clients")
public interface ClientEndpoints {

    @Operation(description = "Créer un nouveau client")
    @ApiResponse(responseCode = "201", description = "Le client a été créé avec succès")
    @ApiResponse(responseCode = "400", description = "La demande de création de client est incorrecte")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    ClientResponseDTO createClient(@Valid @RequestBody ClientCreationRequest clientCreationRequest);

    @Operation(description = "Récupérer un client par son adresse email")
    @ApiResponse(responseCode = "200", description = "Le client a été trouvé")
    @ApiResponse(responseCode = "404", description = "Le client n'a pas été trouvé", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = NotFoundErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("/{email}")
    ClientResponseDTO getClientByEmail(@PathVariable String email);
}

package fr.uga.l3miage.integrator.endpoints;



import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Gestion des clients", description = "Tous les endpoints de gestion des clients")
@RestController
@RequestMapping("/api/clients")
public interface ClientEndpoints {


    @Operation(description = "Récupérer un client par son adresse email")
    @ApiResponse(responseCode = "200", description = "Le client a été trouvé")
    @ApiResponse(responseCode = "404", description = "Le client n'a pas été trouvé")
    @GetMapping("/{email}")
    ClientResponseDTO getClientByEmail(String email);

    @Operation(description = "Récupérer tous les clients")
    @ApiResponse(responseCode = "200", description = "La liste des clients a été récupérée")
    @ApiResponse(responseCode = "404", description = "Aucun client trouvé")
    @GetMapping("/AllClient")
    List<ClientResponseDTO> getAllClients();
}

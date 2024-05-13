package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Gestion des camions", description = "Tous les endpoints de gestion des camions")
@RestController
@RequestMapping("/api/camions")
public interface CamionEndpoints {
    @Operation(description = "Get tous les camions")
    @ApiResponse(
            responseCode = "201",
            description = "Les camions ont été récupérés"
    )
    @ApiResponse(
                    responseCode = "400",
                    description = "Une erreur s'est produite avec la requête de getAllCamions"
            )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/AllCamions")
    List<CamionResponseDTO> getAllCamions();
}

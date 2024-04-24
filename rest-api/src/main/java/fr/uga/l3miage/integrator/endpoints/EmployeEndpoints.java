package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "Gestion des employés", description = "Tous les endpoints des employés")
@RequestMapping("/api/employes")
public interface EmployeEndpoints {

    @Operation(description = "Récupère tous les livreurs")
    @ApiResponse(responseCode = "200", description = "Livreurs récupérés avec succès")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/livreurs")
    Set<EmployeResponseDTO> getAllLivreurs();
}

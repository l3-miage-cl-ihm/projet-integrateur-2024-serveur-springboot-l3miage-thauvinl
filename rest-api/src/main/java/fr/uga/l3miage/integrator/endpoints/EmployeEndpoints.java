package fr.uga.l3miage.integrator.endpoints;


import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Gestion des employés", description = "Tous les endpoints des employés")
@RequestMapping("/api/employes")
public interface EmployeEndpoints {

    @Operation(description = "Récupère tous les livreurs")
    @ApiResponse(responseCode = "200", description = "Livreurs récupérés avec succès")
    @ApiResponse(responseCode = "400", description = "Les livreurs n'ont pas pu étre récupérés")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/livreurs")
    Set<EmployeResponseDTO> getAllLivreurs();


    @Operation(description = "Récupère tous les employes")
    @ApiResponse(responseCode = "200", description = "Employes récupérés avec succès")
    @ApiResponse(responseCode = "400", description = "Les employes n'ont pas pu étre récupérés")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    List<EmployeResponseDTO> getAllEmployes();

    @Operation(description = "Récupère un livreur par son email")
    @ApiResponse(responseCode = "200", description = "Livreur récupéré avec succès")
    @ApiResponse(responseCode = "400", description = "Le livreur n'a pas pu étre récupéré")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/livreurs/{email}")
    EmployeResponseDTO getLivreurByEmail(@PathVariable String email);


}

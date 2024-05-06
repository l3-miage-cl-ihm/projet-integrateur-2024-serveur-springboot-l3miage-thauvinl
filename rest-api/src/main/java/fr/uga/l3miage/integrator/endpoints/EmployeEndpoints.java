package fr.uga.l3miage.integrator.endpoints;

import fr.uga.l3miage.integrator.requests.EmployeCreationRequest;
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
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/livreurs")
    Set<EmployeResponseDTO> getAllLivreurs();

    /*
    @Operation(description = "Crée un nouvel employé")
    @ApiResponse(responseCode = "201", description = "Employé créé avec succès")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    EmployeResponseDTO createEmploye(@RequestBody EmployeCreationRequest request);

    @Operation(description = "Mise à jour des informations d'un employé")
    @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    EmployeResponseDTO updateEmploye(@PathVariable String id, @RequestBody EmployeCreationRequest request);

    @Operation(description = "Supprime un employé")

    @ApiResponse(responseCode = "204", description = "Employé supprimé avec succès")

    @ResponseStatus(HttpStatus.NO_CONTENT)

    @DeleteMapping("/delete/{id}") // corrected here

    void deleteEmploye(@PathVariable String id);


     */

    @GetMapping("/all")
    List<EmployeResponseDTO> getAllEmployes();


}

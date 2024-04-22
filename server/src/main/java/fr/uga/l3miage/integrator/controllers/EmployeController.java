package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/livraisons")
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    @GetMapping("/employes/{tourneeId}/livreurs")
    public ResponseEntity<Set<EmployeEntity>> getLivreursByTourneeId(@PathVariable String tourneeId) throws NotFoundException {
        Set<EmployeEntity> livreurs = employeService.getLivreursByTourneeId(tourneeId);
        return new ResponseEntity<>(livreurs, HttpStatus.OK);
    }

}

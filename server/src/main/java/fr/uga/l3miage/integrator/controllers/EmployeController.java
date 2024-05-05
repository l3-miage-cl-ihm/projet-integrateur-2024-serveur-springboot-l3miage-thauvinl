package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class EmployeController {

    private EmployeService employeService;


    public ResponseEntity<Set<EmployeEntity>> getLivreursByTourneeId(@PathVariable String tourneeId) throws NotFoundException {
        Set<EmployeEntity> livreurs = employeService.getLivreursByTourneeId(tourneeId);
        return new ResponseEntity<>(livreurs, HttpStatus.OK);
    }


    public ResponseEntity<Set<EmployeEntity>> getAllLivreurs() {
        Set<EmployeEntity> livreurs = employeService.getAllLivreurs();
        return new ResponseEntity<>(livreurs, HttpStatus.OK);
    }


}

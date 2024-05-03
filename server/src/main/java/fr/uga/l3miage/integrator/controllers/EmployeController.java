package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.EmployeEndpoints;
import fr.uga.l3miage.integrator.requests.EmployeCreationRequest;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.services.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class EmployeController implements EmployeEndpoints {

    private final EmployeService employeService;

    @Override
    public Set<EmployeResponseDTO> getAllLivreurs() {
        return employeService.getAllLivreurs();
    }


    @Override
    public EmployeResponseDTO createEmploye(EmployeCreationRequest request) {
        return employeService.createEmploye(request);
    }

    @Override
    public EmployeResponseDTO updateEmploye(String id, EmployeCreationRequest request) {
        return employeService.updateEmploye(id, request);
    }

    @Override
    public void deleteEmploye(String id) {
        employeService.deleteEmploye(id);
    }
}

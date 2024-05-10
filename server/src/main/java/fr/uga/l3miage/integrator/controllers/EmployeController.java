package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.endpoints.EmployeEndpoints;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.services.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
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
    public List<EmployeResponseDTO> getAllEmployes() {
        return employeService.getAllEmployes();
    }

    @Override
    public EmployeResponseDTO getLivreurByEmail(String email) {
        return employeService.getLivreurByEmail(email);
    }

}
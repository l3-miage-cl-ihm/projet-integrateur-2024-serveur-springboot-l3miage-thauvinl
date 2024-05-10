package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.EmployeComponent;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.EmployeMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeComponent employeComponent;
    private final EmployeMapper employeMapper;



    public List<EmployeResponseDTO> getAllEmployes() {
            List<EmployeEntity> employes = employeComponent.getAllEmployes();
            return employes.stream()
                    .map(employeMapper::toResponse)
                    .collect(Collectors.toList());
    }



    public Set<EmployeResponseDTO> getAllLivreurs() {

            Set<EmployeEntity> livreurs = employeComponent.getAllLivreurs();
            return livreurs.stream()
                    .map(employeMapper::toResponse)
                    .collect(Collectors.toSet());
    }
    public EmployeResponseDTO getLivreurByEmail(String email) {
        try {
            EmployeEntity livreur = employeComponent.getLivreurByEmail(email);
            return employeMapper.toResponse(livreur);
        } catch (NotFoundEmployeEntityException e) {
            e.printStackTrace();
            return null;
        }
    }

}
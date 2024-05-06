package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.EmployeComponent;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.mappers.EmployeMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.requests.EmployeCreationRequest;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    public Set<EmployeResponseDTO> getLivreursByTourneeId(String tourneeId) {
        try {
            Set<EmployeEntity> livreurs = employeComponent.getLivreursByTourneeId(tourneeId);
            return livreurs.stream()
                    .map(employeMapper::toResponse)
                    .collect(Collectors.toSet());
        } catch (NotFoundException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    public List<EmployeResponseDTO> getAllEmployes() {
        List<EmployeEntity> employes = employeComponent.getAllEmployes();
        return employes.stream()
                .map(employeMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeResponseDTO getEmployeById(String id) throws NotFoundEmployeEntityException {
        try {
            EmployeEntity employeEntity = employeComponent.getEmployeById(id);
            if (employeEntity != null) {
                return employeMapper.toResponse(employeEntity);
            } else {
                throw new NotFoundEmployeEntityException("Employé non trouvé avec l'ID: " + id);
            }
        } catch (NotFoundEmployeEntityException e) {
            // Handle NotFoundException here, or rethrow it if necessary
            throw new NotFoundEmployeEntityException("Employé non trouvé avec l'ID: " + id);
        }
    }






    public EmployeResponseDTO createEmploye(EmployeCreationRequest request) {
        EmployeEntity employe = employeMapper.toEntity(request);
        EmployeEntity createdEmploye = employeComponent.createEmploye(employe);
        return employeMapper.toResponse(createdEmploye);
    }

    public EmployeResponseDTO updateEmploye(String id, EmployeCreationRequest request) {
        EmployeEntity employe = employeMapper.toEntity(request);
        EmployeEntity updatedEmploye = employeComponent.updateEmploye(id, employe);
        return employeMapper.toResponse(updatedEmploye);
    }

    public void deleteEmploye(String id) {
        employeComponent.deleteEmploye(id);
    }

    public Set<EmployeResponseDTO> getAllLivreurs() {
        Set<EmployeEntity> livreurs = employeComponent.getAllLivreurs();
        return livreurs.stream()
                .map(employeMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
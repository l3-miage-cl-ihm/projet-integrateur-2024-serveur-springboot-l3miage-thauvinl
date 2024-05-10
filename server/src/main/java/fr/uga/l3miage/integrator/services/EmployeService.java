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


    public Set<EmployeResponseDTO> getLivreursByTourneeId(String tourneeId) {
        try {
            Set<EmployeEntity> livreurs = employeComponent.getLivreursByTourneeId(tourneeId);
            return livreurs.stream()
                    .map(employeMapper::toResponse)
                    .collect(Collectors.toSet());
        } catch (NotFoundTourneeEntityException e) {
            //indique où l'exception a été levée et quelle était la séquence d'appels de méthode qui a conduit à cette exception
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
            throw new NotFoundEmployeEntityException("Employé non trouvé avec l'ID: " + id);
        }
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
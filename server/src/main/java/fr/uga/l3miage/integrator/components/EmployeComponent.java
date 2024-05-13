package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeComponent {
    private final EmployeRepository employeRepository;
    private final TourneeRepository tourneeRepository;

    public Set<EmployeEntity> getLivreursByTourneeId(String tourneeId) throws NotFoundTourneeEntityException {
        Optional<TourneeEntity> tourneeOptional = tourneeRepository.findById(tourneeId);
        return tourneeOptional.map(tournee -> {
            // Filtrer les employés pour ne garder que ceux qui sont des livreurs
            return tournee.getEmployeEntitySet().stream()
                    .filter(employe -> employe.getEmploi() == Emploi.livreur)
                    .collect(Collectors.toSet());
        }).orElseThrow(() -> new NotFoundTourneeEntityException("Tournée non trouvée avec l'ID : " + tourneeId));
    }


    public List<EmployeEntity> getAllEmployes() {
            return employeRepository.findAll().stream().collect(Collectors.toList());

    }

    public EmployeEntity getEmployeById(String trigramme) throws NotFoundEmployeEntityException {
        return employeRepository.findByTrigramme(trigramme).orElseThrow(()->new NotFoundEmployeEntityException(String.format("L'employé d'id %s n'existe pas", trigramme)));
    }

    public EmployeEntity updateEmploye(String id, EmployeEntity employe) throws NotFoundEmployeEntityException {
        return employeRepository.findById(id).orElseThrow(()->new NotFoundEmployeEntityException(String.format("Employe introuvable")));
    }


    public EmployeEntity getLivreurByEmail(String email) throws NotFoundEmployeEntityException {
        return  employeRepository.findByEmailAndEmploi(email, Emploi.livreur).orElseThrow(()->new NotFoundEmployeEntityException(String.format("Livreur non trouvé avec l'email: " + email)));

    }

    public Set<EmployeEntity> getAllLivreurs() {
        return employeRepository.findAllByEmploi(Emploi.livreur);

    }



}

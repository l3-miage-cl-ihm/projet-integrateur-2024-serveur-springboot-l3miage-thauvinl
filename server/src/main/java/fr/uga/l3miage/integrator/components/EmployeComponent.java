package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
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


    public List<EmployeEntity> getAllEmployes() {
            return employeRepository.findAll().stream().collect(Collectors.toList());

    }

    public EmployeEntity getEmployeById(String trigramme) throws NotFoundEmployeEntityException {
        return employeRepository.findByTrigramme(trigramme).orElseThrow(()->new NotFoundEmployeEntityException(String.format("L'employé d'id %s n'existe pas", trigramme)));
    }

    public EmployeEntity updateEmploye(String id, EmployeEntity employe) throws NotFoundEmployeEntityException {
        Optional<EmployeEntity> optionalEmploye = employeRepository.findById(id);
        if (optionalEmploye.isPresent()) {
            EmployeEntity existingEmploye = optionalEmploye.get();
            // Mettre à jour les attributs de l'employé existant avec les valeurs de l'employé fourni
            existingEmploye.setEmail(employe.getEmail());
            existingEmploye.setNom(employe.getNom());
            existingEmploye.setPrenom(employe.getPrenom());
            existingEmploye.setTelephone(employe.getTelephone());
            existingEmploye.setEmploi(employe.getEmploi());
            // Enregistrer l'employé mis à jour
            return employeRepository.save(existingEmploye);
        } else {
            // gérer le cas où l'employé avec l'ID donné n'existe pas
            throw new NotFoundEmployeEntityException("Employe introuvable");
        }
    }


    public EmployeEntity getLivreurByEmail(String email) throws NotFoundEmployeEntityException {
        Optional<EmployeEntity> optionalEmploye = employeRepository.findByEmailAndEmploi(email, Emploi.livreur);
        if (optionalEmploye.isPresent()) {
            return optionalEmploye.get();
        } else {
            throw new NotFoundEmployeEntityException("Livreur non trouvé avec l'email: " + email);
        }
    }

    public Set<EmployeEntity> getAllLivreurs() {

            return employeRepository.findAllByEmploi(Emploi.livreur);

    }



}

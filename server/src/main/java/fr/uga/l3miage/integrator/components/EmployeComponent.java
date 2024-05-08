package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import javassist.NotFoundException;
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

    public Set<EmployeEntity> getLivreursByTourneeId(String tourneeId) throws NotFoundException {
        Optional<TourneeEntity> tourneeOptional = tourneeRepository.findById(tourneeId);
        if (tourneeOptional.isPresent()) {
            TourneeEntity tournee = tourneeOptional.get();
            // Filtrer les employés pour ne garder que ceux qui sont des livreurs
            return tournee.getEmployeEntitySet().stream()
                    .filter(employe -> employe.getEmploi() == Emploi.livreur)
                    .collect(Collectors.toSet());
        } else {
            // Gérer le cas où la tournée n'existe pas
            throw new NotFoundException("Tournée non trouvée avec l'ID : " + tourneeId);
        }
    }

    public List<EmployeEntity> getAllEmployes() {
        return employeRepository.findAll().stream().collect(Collectors.toList());
    }

    public EmployeEntity getEmployeById(String trigramme) throws NotFoundEmployeEntityException {
        return employeRepository.findByTrigramme(trigramme).orElseThrow(()->new NotFoundEmployeEntityException(String.format("L'employé d'id %s n'existe pas", trigramme)));
    }

    public EmployeEntity createEmploye(EmployeEntity employe) {
        return employeRepository.save(employe);
    }

    public EmployeEntity updateEmploye(String id, EmployeEntity employe) {
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
            return null; // Ou gérer le cas où l'employé avec l'ID donné n'existe pas
        }
    }

    public void deleteEmploye(String id) {
        employeRepository.deleteById(id);
    }

    public Set<EmployeEntity> getAllLivreurs() {
        return employeRepository.findAllByEmploi(Emploi.livreur);
    }

    public EmployeEntity getLivreurByEmail(String email) throws NotFoundEmployeEntityException {
        Optional<EmployeEntity> optionalEmploye = employeRepository.findByEmailAndEmploi(email, Emploi.livreur);
        if (optionalEmploye.isPresent()) {
            return optionalEmploye.get();
        } else {
            throw new NotFoundEmployeEntityException("Livreur non trouvé avec l'email: " + email);
        }
    }


}

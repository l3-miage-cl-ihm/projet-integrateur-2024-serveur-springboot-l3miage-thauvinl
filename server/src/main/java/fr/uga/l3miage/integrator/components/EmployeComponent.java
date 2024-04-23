package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeComponent {
    private TourneeRepository tourneeRepository;
    private EmployeRepository employeRepository;
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
    public Set<EmployeEntity> getAllLivreurs(){
        Set<EmployeEntity> employeEntities=employeRepository.findAllByEmploi(Emploi.livreur);
        return  employeEntities;
    }
}

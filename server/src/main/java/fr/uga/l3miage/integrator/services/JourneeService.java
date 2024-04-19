package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JourneeService {
    @Autowired
    private final JourneeComponent journeeComponent;

    public Optional<JourneeEntity> findJourneeByReference(String reference) {
        // Implémentation de la logique pour trouver une Journee par sa référence
        return journeeComponent.findJourneeByReference(reference);
    }

    public List<JourneeEntity> findAllJournees(){
        return journeeComponent.findAllJournees();
    }

    public JourneeEntity createJournee(JourneeEntity journee) {
        return journeeComponent.createJournee(journee);
    }

    public List<TourneeEntity> getAllTourneesOfJournee(String reference) {
        // Implémentez la logique pour récupérer toutes les tournées associées à une journée spécifique
        // en utilisant la référence de la journée.
        return journeeComponent.getAllTourneesOfJournee(reference);
    }



}

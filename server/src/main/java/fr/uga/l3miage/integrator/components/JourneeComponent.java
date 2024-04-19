package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JourneeComponent {
    @Autowired
   private final JourneeRepository journeeRepository;

   @Autowired
   private TourneeRepository tourneeRepository;

    public Optional<JourneeEntity> findJourneeByReference(String reference) {
        // Implémentation de la logique pour trouver une Journee par sa référence
        return journeeRepository.findByReference(reference);
    }

    public List<JourneeEntity> findAllJournees(){
        return journeeRepository.findAll();
    }

    public JourneeEntity createJournee(JourneeEntity journee) {
        return journeeRepository.save(journee);
    }

    public List<TourneeEntity> getAllTourneesOfJournee(String reference) {
        // Implémentez la logique pour récupérer toutes les tournées associées à une journée spécifique
        // en utilisant la référence de la journée.
        return tourneeRepository.findByReferenceContaining(reference);
    }
}

package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JourneeService {

    private final JourneeRepository journeeRepository;

    private TourneeRepository tourneeRepository;
    @Autowired
    public JourneeService(JourneeRepository journeeRepository, TourneeRepository tourneeRepository) {
        this.journeeRepository = journeeRepository;
        this.tourneeRepository=tourneeRepository;
    }

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

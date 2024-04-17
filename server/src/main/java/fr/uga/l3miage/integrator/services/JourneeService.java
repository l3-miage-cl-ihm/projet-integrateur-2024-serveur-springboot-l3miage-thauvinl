package fr.uga.l3miage.integrator.services;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JourneeService {

    private final JourneeRepository journeeRepository;

    @Autowired
    public JourneeService(JourneeRepository journeeRepository) {
        this.journeeRepository = journeeRepository;
    }

    public Optional<JourneeEntity> findJourneeByReference(String reference) {
        // Implémentation de la logique pour trouver une Journee par sa référence
        return journeeRepository.findByReference(reference);
    }

    public List<JourneeEntity> findAllJournees(){
        return journeeRepository.findAll();
    }

   /* @Transactional
    public JourneeEntity updateJournee(String reference, JourneeEntity journeeDetails) {
        Optional<JourneeEntity> journeeOptional = journeeRepository.findByReference(reference);
        if(journeeOptional.isPresent()) {
            JourneeEntity existingJournee = journeeOptional.get();
            existingJournee.setDate(journeeDetails.getDate());
            existingJournee.setMontant(journeeDetails.getMontant());
            existingJournee.setReference(journeeDetails.getReference());
            return journeeRepository.save(existingJournee);
        } else {
            return null;
        }
    }*/
}

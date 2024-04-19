package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
@Component
@RequiredArgsConstructor
public class TourneeComponent {
   private final TourneeRepository tourneeRepository;
   private final JourneeRepository journeeRepository;


    /*public ResponseEntity<TourneeEntity> createTournee(TourneeEntity tournee) {
        Optional<JourneeEntity> optionalJournee = journeeRepository.findByReference(tournee.getReference());
        if (optionalJournee.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(tourneeRepository.save(tournee));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }*/
}

package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourneeService {
    @Autowired
    private TourneeRepository tourneeRepository;
    @Autowired
    private JourneeRepository journeeRepository;
    public ResponseEntity<TourneeEntity> createTournee(TourneeEntity tournee) {
        Optional<JourneeEntity> optionalJournee = journeeRepository.findByReference(tournee.getReference());
        if (optionalJournee.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(tourneeRepository.save(tournee));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}

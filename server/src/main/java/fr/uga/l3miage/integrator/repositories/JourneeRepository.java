package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneeRepository extends JpaRepository<JourneeEntity, String> {
    Optional<JourneeEntity> findByReference(String reference);
}

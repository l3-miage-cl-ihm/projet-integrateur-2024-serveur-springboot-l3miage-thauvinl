package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.LigneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneRepository extends JpaRepository<LigneEntity, String> {
}

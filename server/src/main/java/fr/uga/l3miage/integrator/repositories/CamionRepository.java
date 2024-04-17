package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.models.HealthcheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamionRepository extends JpaRepository<CamionEntity,Long> {
}

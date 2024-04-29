package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<ProduitEntity, String> {
}

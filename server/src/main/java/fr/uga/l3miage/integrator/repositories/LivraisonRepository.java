package fr.uga.l3miage.integrator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import java.util.Set;

@Repository
public interface LivraisonRepository extends JpaRepository<LivraisonEntity, String> {
    // Vous pouvez ajouter des méthodes spécifiques de requête ici si nécessaire
    LivraisonEntity findLivraisonEntityByReference(String reference);

}

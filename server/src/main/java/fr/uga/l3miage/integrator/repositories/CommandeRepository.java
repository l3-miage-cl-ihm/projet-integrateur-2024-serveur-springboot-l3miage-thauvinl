package fr.uga.l3miage.integrator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import java.util.Set;

@Repository
public interface CommandeRepository extends JpaRepository<CommandeEntity, String> {
    // Vous pouvez ajouter des méthodes spécifiques de requête ici si nécessaire
    Set<CommandeEntity> findCommandeEntitiesByReference(String reference);
}


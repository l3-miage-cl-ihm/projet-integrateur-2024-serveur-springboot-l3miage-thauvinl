package fr.uga.l3miage.integrator.repositories;


import fr.uga.l3miage.integrator.models.LivraisonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import java.util.Set;

@Repository
public interface CommandeRepository extends JpaRepository<CommandeEntity, String> {

    CommandeEntity findCommandeEntityByReference(String reference);
    Set<CommandeEntity> findCommandeEntitiesByLivraison(LivraisonEntity livraison);





}


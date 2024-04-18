package fr.uga.l3miage.integrator.repository;


import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommandeRepositoryTest {

    @Autowired
    private CommandeRepository commandeRepository;

    @Test
    public void testFindCommandeEntitiesByReference() {
        // Création d'une commande avec une référence
        CommandeEntity commande = new CommandeEntity();
        commande.setReference("CMD123");
        commandeRepository.save(commande);

        // Recherche de la commande par référence
        Set<CommandeEntity> commandesTrouvees = commandeRepository.findCommandeEntitiesByReference("CMD123");

        // Vérification que la commande trouvée est celle que nous avons enregistrée
        assertEquals(1, commandesTrouvees.size());
        assertEquals("CMD123", commandesTrouvees.iterator().next().getReference());
    }

    @Test
    public void testFindCommandeEntitiesByLivraisonEntity() {
        // Création d'une livraison
        LivraisonEntity livraison = new LivraisonEntity();
        livraison.setReference("Livraison1");

        // Création de deux commandes associées à cette livraison
        CommandeEntity commande1 = new CommandeEntity();
        commande1.setReference("CMD1");
        commande1.setLivraisonEntity(livraison);

        CommandeEntity commande2 = new CommandeEntity();
        commande2.setReference("CMD2");
        commande2.setLivraisonEntity(livraison);

        commandeRepository.saveAll(Set.of(commande1, commande2));

        // Recherche des commandes par livraison
        Set<CommandeEntity> commandesTrouvees = commandeRepository.findCommandeEntitiesByLivraisonEntity(livraison);

        // Vérification que les commandes trouvées sont celles associées à la livraison
        assertEquals(2, commandesTrouvees.size());
        Set<String> references = new HashSet<>();
        for (CommandeEntity commande : commandesTrouvees) {
            references.add(commande.getReference());
        }
        assertEquals(Set.of("CMD1", "CMD2"), references);
    }
}

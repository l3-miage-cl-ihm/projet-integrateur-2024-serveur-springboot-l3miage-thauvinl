package fr.uga.l3miage.integrator.repository;


import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class CommandeRepositoryTest {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Test
    public void testFindCommandeEntityByReference() {
        String reference = "ref123";

        CommandeEntity commandeEntity = CommandeEntity.builder()
                .reference(reference)
                .build();

        commandeRepository.save(commandeEntity);
        Optional<CommandeEntity> result = commandeRepository.findCommandeEntityByReference(reference);

        assertTrue(result.isPresent());
        assertEquals(commandeEntity.getReference(), result.get().getReference());
    }


    @Test
    public void testFindCommandeEntitiesByLivraisonEntity() {

        LivraisonEntity livraison = new LivraisonEntity();
        livraison.setReference("Livraison1");
        livraisonRepository.save(livraison);

        CommandeEntity commande1 = new CommandeEntity();
        commande1.setReference("CMD1");
        commande1.setLivraison(livraison);


        CommandeEntity commande2 = new CommandeEntity();
        commande2.setReference("CMD2");
        commande2.setLivraison(livraison);

        commandeRepository.saveAll(Set.of(commande1, commande2));


        Set<CommandeEntity> commandesTrouvees = commandeRepository.findCommandeEntitiesByLivraison(livraison);


        assertEquals(2, commandesTrouvees.size());
        Set<String> references = new HashSet<>();
        for (CommandeEntity commande : commandesTrouvees) {
            references.add(commande.getReference());
        }
        assertEquals(Set.of("CMD1", "CMD2"), references);

    }

}

package fr.uga.l3miage.integrator.repository;


import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import org.apache.catalina.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommandeRepository commandeRepository;
    @Test
    public void testFindByCommandesReference() {
        // Création d'un client
        ClientEntity client = new ClientEntity();
        client.setEmail("test@gmail.com");

        // Création d'une commande associée à ce client
        CommandeEntity commande = new CommandeEntity();
        commande.setReference("CMD123");
        commandeRepository.save(commande);

        // Ajout de la commande au client
        client.setCommandes(Set.of(commande));

        // Enregistrement du client
        clientRepository.save(client);

        // Recherche du client par référence de commande
        ClientEntity clientTrouve = clientRepository.findClientEntityByCommandes(commande);

        // Vérification que le client trouvé est celui que nous avons enregistré
        assertNotNull(clientTrouve);
        assertEquals("test@gmail.com", clientTrouve.getEmail());
    }

}

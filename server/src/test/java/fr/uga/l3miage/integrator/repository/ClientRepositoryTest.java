package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ClientRepositoryTest {
    /*

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFindClientEntityByEmail() {
        // Given
        String email = "test@example.com";
        ClientEntity clientEntity = ClientEntity.builder()
                .email(email)
                .nom("John Doe")
                .build();

        // Save the client entity to the database
        clientRepository.save(clientEntity);

        // When
        ClientEntity foundClient = clientRepository.findClientEntityByEmail(email);

        // Then
        assertNotNull(foundClient);
        assertEquals(email, foundClient.getEmail());
    }

    @Test
    void testFindClientEntityByCommandes() {
        // Given
        // Create a command entity
        CommandeEntity commandeEntity = new CommandeEntity();
        // Set up the command entity properties

        // Create a client entity and associate it with the command entity
        ClientEntity clientEntity = ClientEntity.builder()
                .email("test@example.com")
                .nom("John Doe")
                .commandes(new HashSet<>()) // Initialize commandes set
                .build();
        clientEntity.getCommandes().add(commandeEntity);

        // Save the client entity to the database
        clientRepository.save(clientEntity);

        // When
        ClientEntity foundClient = clientRepository.findClientEntityByCommandes(commandeEntity);

        // Then
        assertNotNull(foundClient);
        // Perform additional assertions as needed
    }
    
 */
}

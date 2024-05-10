package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class ClientRepositoryTest {


    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFindClientEntityByCommandes() {
        CommandeEntity commande = CommandeEntity.builder()
                .reference("01")
                .build();
        clientRepository.save(ClientEntity.builder()
                .email("test@test.com")
                .commandes(Set.of(commande))
                .build());

        Optional<ClientEntity> response = clientRepository.findClientEntityByCommandes(commande);
        assertTrue(response.isPresent());
        assertThat(response.get().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void testFindClientEntityByEmail() {
        String email = "test@test.com";
        clientRepository.save(ClientEntity.builder()
                .email(email)
                .build());

        Optional<ClientEntity> response = clientRepository.findClientEntityByEmail(email);
        assertTrue(response.isPresent());
        assertThat(response.get().getEmail()).isEqualTo(email);
    }
}

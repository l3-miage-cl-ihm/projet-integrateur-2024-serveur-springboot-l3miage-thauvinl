package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ClientComponentTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientComponent clientComponent;

    @Test
    public void testGetClientByEmailSuccess() throws NotFoundClientEntityException {
        // Given
        String email = "test@example.com";
        ClientEntity expectedClient = ClientEntity.builder()
                .email(email)
                .nom("King Julian")
                .build();

        when(clientRepository.findClientEntityByEmail(email)).thenReturn(Optional.of((expectedClient)));

        // When
        ClientEntity actualClient = clientComponent.getClientByEmail(email);

        // Then
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testGetClientByEmailNotFound() {
        // Given
        String email = "nonexistent@example.com";

        // Mocking behavior of clientRepository to return empty optional
        when(clientRepository.findClientEntityByEmail(email)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundClientEntityException.class, () -> {
            clientComponent.getClientByEmail(email);
        });
    }

}



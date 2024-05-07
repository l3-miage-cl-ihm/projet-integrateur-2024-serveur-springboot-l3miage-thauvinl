package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundClientEntityExeption;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
public class ClientComponentTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    @MockBean
    private ClientComponent clientComponent;

    @Test
    public void testGetClientByEmailSuccess() throws NotFoundClientEntityExeption {
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
    public void testGetClientByEmailNotFound() throws NotFoundClientEntityExeption {
        // Given
        String email = "nonexistent@example.com";

        // Mocking behavior of clientRepository to return empty optional
        when(clientRepository.findClientEntityByEmail(email)).thenReturn(Optional.empty());

        // When
        ClientEntity actualClient = clientComponent.getClientByEmail(email);

        // Then
        assertEquals(null, actualClient);
    }
}

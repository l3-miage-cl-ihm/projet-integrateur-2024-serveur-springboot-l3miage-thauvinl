package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientComponentTest {/*

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientComponent clientComponent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetClientByEmailSuccess() {
        // Given
        String email = "test@example.com";
        ClientEntity expectedClient = new ClientEntity();
        expectedClient.setEmail(email);
        expectedClient.setNom("John Doe");

        // Set up mock behavior
        when(clientRepository.findClientEntityByEmail(email)).thenReturn(expectedClient);

        // When
        ClientEntity actualClient = clientComponent.getClientByEmail(email);

        // Then
        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testGetClientByEmailNotFound() {
        // Given
        String email = "nonexistent@example.com";

        // Set up mock behavior to return null when client is not found
        when(clientRepository.findClientEntityByEmail(email)).thenReturn(null);

        // When
        ClientEntity actualClient = clientComponent.getClientByEmail(email);

        // Then
        assertEquals(null, actualClient);
    }

   */

}

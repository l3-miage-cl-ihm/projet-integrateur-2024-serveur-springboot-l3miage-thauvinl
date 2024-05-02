package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.ClientComponent;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Mock
    private ClientComponent clientComponent;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /*
    @Test
    public void testGetClientByEmail() {
        // Given
        String email = "test@example.com";
        ClientEntity expectedClient = new ClientEntity();
        expectedClient.setEmail(email);

        // Mocking the component behavior
        when(clientComponent.getClientByEmail(email)).thenReturn(expectedClient);

        // When
        ClientEntity actualClient = clientService.getClientByEmail(email);

        // Then
        assertEquals(expectedClient, actualClient);
    }

     */
}

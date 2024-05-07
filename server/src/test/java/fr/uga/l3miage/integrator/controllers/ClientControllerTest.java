package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import fr.uga.l3miage.integrator.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ClientControllerTest {
/*
    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetClientByEmailSuccess() {

        String email = "test@mail.com";

        // Given
        ClientEntity expectedClient = ClientEntity
                .builder()
                .email(email)
                .nom("Damiano")
                .build();


        when(clientService.getClientByEmail(email)).thenReturn(
                ClientResponseDTO.builder()
                        .email(expectedClient.getEmail())
                        .prenom("John")
                        .nom(expectedClient.getNom())
                        .etat("active")
                        .montantTotal(100.0f)
                        .adresse(null)
                        .build()
        );

        // When
        ClientResponseDTO actualClient = clientController.getClientByEmail(email);

        // Then
        assertEquals(expectedClient.getEmail(), actualClient.getEmail());
        assertEquals(expectedClient.getNom(), actualClient.getNom());
    }


    @Test
    void testGetAllClients() {
        // Given
        List<ClientResponseDTO> expectedClients = Arrays.asList(
                ClientResponseDTO.builder().email("client1@example.com").nom("Client 1").build(),
                ClientResponseDTO.builder().email("client2@example.com").nom("Client 2").build()
        );


        when(clientService.getAllClients()).thenReturn(expectedClients);

        // When
        List<ClientResponseDTO> actualClients = clientController.getAllClients();

        // Then
        assertEquals(expectedClients.size(), actualClients.size());
        for (int i = 0; i < expectedClients.size(); i++) {
            assertEquals(expectedClients.get(i).getEmail(), actualClients.get(i).getEmail());
            assertEquals(expectedClients.get(i).getNom(), actualClients.get(i).getNom());
        }
    }

 */
}

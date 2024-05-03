package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.mappers.ClientMapper;
import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import fr.uga.l3miage.integrator.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientControllerTest {
    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
/*
    @Test
    void getClientByEmail_ClientFound_ReturnsClientResponseDTO() {
        // Arrange
        String email = "test@example.com";
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(email);

        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setEmail(email);

        when(clientService.getClientByEmail(email)).thenReturn(clientEntity);
        when(clientMapper.toResponse(clientEntity)).thenReturn(clientResponseDTO);

        // Act
        ResponseEntity<ClientResponseDTO> response = clientController.getClientByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientResponseDTO, response.getBody());
    }

 */
/*
    @Test
    void getClientByEmail_ClientNotFound_ReturnsNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(clientService.getClientByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity<ClientResponseDTO> response = clientController.getClientByEmail(email);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
 */
}

package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.ClientEntity;
import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.responses.ClientResponseDTO;
import fr.uga.l3miage.integrator.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class ClientControllerTest {
    /*

    @Mock
    private ClientService clientService;

    @Autowired
    private ClientController clientController;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetClientByEmailSuccess() {
        // Given
        String email = "test@mail.com";
        ClientEntity expectedClient = ClientEntity.builder()
                .email(email)
                .nom("Damiano")
                .build();
        clientRepository.save(expectedClient);


        // ResponseEntity<SessionResponse> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, SessionResponse.class);

        // When
        ClientResponseDTO actualClient = testRestTemplate.postForObject("/api/clients")

        // Then
        assertEquals(expectedClient.getEmail(), actualClient.getEmail());
        assertEquals(expectedClient.getNom(), actualClient.getNom());
    }

    @Test
    void testGetAllClients() {
        // Given
        /*
        List<ClientResponseDTO> expectedClients = Arrays.asList(
                ClientResponseDTO.builder().email("client1@example.com").nom("Client 1").build(),
                ClientResponseDTO.builder().email("client2@example.com").nom("Client 2").build()
        );



        ClientResponseDTO client1 = ClientResponseDTO
                .builder()
                .email("slim@shady.com")
                .nom("Marshal")
                .build();

        ClientResponseDTO client2 = ClientResponseDTO
                .builder()
                .email("dontlethimnearurkids@jackson.com")
                .nom("Michael")
                .build();

        List<ClientResponseDTO> expectedClients = new ArrayList<>();
        expectedClients.add(client1);
        expectedClients.add(client2);

        ClientEntity clientA = ClientEntity
                .builder()
                .email("slim@shady.com")
                .nom("Marshal")
                .build();

        ClientEntity clientB = ClientEntity
                .builder()
                .email("dontlethimnearurkids@jackson.com")
                .nom("MIchael")
                .build();


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

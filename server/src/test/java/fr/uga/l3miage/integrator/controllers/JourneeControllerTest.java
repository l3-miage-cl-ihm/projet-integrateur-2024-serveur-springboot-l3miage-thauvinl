package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class JourneeControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    TourneeRepository tourneeRepository;

    @SpyBean
    JourneeComponent journeeComponent;

    @SpyBean
    TourneeComponent tourneeComponent;

    @SpyBean
    JourneeService journeeService;

    @AfterEach
    public void clearDatabase() {
        journeeRepository.deleteAll();
        tourneeRepository.deleteAll();
    }

    @Test
    void createJourneeSuccess(){
        //given
        final HttpHeaders headers = new HttpHeaders();

        final JourneeCreationRequest request = JourneeCreationRequest
                .builder()
                .reference("Test")
                .tournees(Set.of())
                .build();

        // when
        ResponseEntity<JourneeResponseDTO> response = testRestTemplate.exchange("/api/journees/create", HttpMethod.POST, new HttpEntity<>(request, headers), JourneeResponseDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(journeeRepository.count()).isEqualTo(1);
        verify(journeeComponent, times(1)).createJournee(any(JourneeEntity.class));
    }

    @Test
    void createJourneeFail(){
        //given
        final HttpHeaders headers = new HttpHeaders();

        final JourneeCreationRequest request = JourneeCreationRequest
                .builder()
                .reference(null)
                .tournees(Set.of())
                .build();
        //when
        ResponseEntity<String> response = testRestTemplate.exchange("/api/journees/create", HttpMethod.POST, new HttpEntity<>(request, headers), String.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getJourneeSuccess(){
        final JourneeCreationRequest request = JourneeCreationRequest
                .builder()
                .reference("Trouve")
                .tournees(Set.of())
                .build();

        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("idJournee", "Trouve");

        JourneeResponseDTO expectedResponse= journeeService.createJournee(request);
        ResponseEntity<JourneeResponseDTO> response = testRestTemplate.exchange("/api/journees/{idJournee}", HttpMethod.GET, new HttpEntity<>(null, headers), JourneeResponseDTO.class, urlParams);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(journeeService, times(1)).createJournee(any(JourneeCreationRequest.class));
        verify(journeeComponent, times(1)).createJournee(any(JourneeEntity.class));
    }

    @Test
    void getJourneeNotFound(){
        //Given
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("idJournee", "NonExistant");

        NotFoundErrorResponse notFoundErrorResponseExpected = NotFoundErrorResponse
                .builder()
                .uri("/api/journees/NonExistant")
                .errorMessage("La journée [NonExistant] n'a pas été trouvée")
                .build();

        //when
        ResponseEntity<NotFoundErrorResponse> response = testRestTemplate.exchange("/api/journees/{idJournee}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).usingRecursiveComparison()
                .isEqualTo(notFoundErrorResponseExpected);
    }
    /*@Transactional
    @Test
    void addTourneeInJourneeSucces() {
        //Given
        final JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("Test")
                .tournees(Set.of())
                .build();
        final TourneeEntity tournee = TourneeEntity.builder()
                .reference("T001")
                .build();

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("idJournee", "Test");
        urlParams.put("idTournee", "T001");

        //When
        JourneeResponseDTO journeeResponseDTO = journeeService.createJournee(request);
        TourneeEntity registered = tourneeRepository.save(tournee);

        //Add song to playlist through its service
        JourneeResponseDTO expectedResponse = journeeService.addTourneeInJournee(journeeResponseDTO.getReference(), registered.getReference());

        //targeting the endpoint
        ResponseEntity<JourneeResponseDTO> response = testRestTemplate.exchange("/api/journees/{idJournee}/add?idTournee={idTournee}", HttpMethod.PATCH, new HttpEntity<>(headers), JourneeResponseDTO.class, urlParams);

        //Then  -- assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        verify(journeeService, times(2)).addTourneeInJournee(any(String.class), any(String.class));
    }
}*/

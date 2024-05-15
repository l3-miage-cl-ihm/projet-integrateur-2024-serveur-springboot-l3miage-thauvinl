package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.models.enums.EtatDeCommande;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
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
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class JourneeControllerTest {

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @AfterEach
    void deleteAll(){
        journeeRepository.deleteAll();
    }
    @Test
    public void getJourneeByRefSuccess(){
        JourneeEntity journee = JourneeEntity.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .build();
        journeeRepository.save(journee);
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("idJournee", "test");
        JourneeResponseDTO expected = JourneeResponseDTO.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .montant(0.0)
                .tempsDeMontageTheorique(0)
                .distanceAParcourir(0.0)
                .tournees(new HashSet<>())
                .build();
        ResponseEntity<JourneeResponseDTO> actual = testRestTemplate.exchange("/api/journees/{idJournee}", HttpMethod.GET, new HttpEntity<>(null, headers), JourneeResponseDTO.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void getJourneeByRefShouldReturn404Status(){
        final HttpHeaders headers = new HttpHeaders();
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("idJournee", "testNotFound");
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/journees/testNotFound")
                .errorMessage("La journée [testNotFound] n'a pas été trouvée")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/journees/{idJournee}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void createJourneeSuccess(){

        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .tournees(Set.of())
                .build();
        final HttpHeaders headers = new HttpHeaders();
        JourneeResponseDTO expected = JourneeResponseDTO.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .montant(0.0)
                .tempsDeMontageTheorique(0)
                .distanceAParcourir(0.0)
                .tournees(new HashSet<>())
                .build();
        ResponseEntity<JourneeResponseDTO> actual = testRestTemplate.exchange("/api/journees/create", HttpMethod.POST, new HttpEntity<>(request,headers), JourneeResponseDTO.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void createJourneeNotFoundEmployeShouldReturn404StatusCode(){
        Set<String> employeIds = new HashSet<>();
        employeIds.add("NotFound");
        TourneeCreationRequest tourneeCreationRequest = TourneeCreationRequest.builder()
                .reference("testTournee")
                .employesIds(employeIds)
                .build();
        Set<TourneeCreationRequest> tournees = new HashSet<>();
        tournees.add(tourneeCreationRequest);
        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .tournees(tournees)
                .build();
        final HttpHeaders headers = new HttpHeaders();
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/journees/create")
                .errorMessage("L'employé d'id NotFound n'existe pas")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/journees/create", HttpMethod.POST, new HttpEntity<>(request, headers), NotFoundErrorResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void createJourneeCommandeNotFoundShouldReturn404StatusCode(){
        Set<String> commandeRefs = new HashSet<>();
        commandeRefs.add("NotFound");
        Set<LivraisonCreationRequest> livraisons = new HashSet<>();
        LivraisonCreationRequest livraisonCreationRequest = LivraisonCreationRequest.builder()
                .reference("testLivraison")
                .refCommande(commandeRefs)
                .build();
        livraisons.add(livraisonCreationRequest);
        Set<TourneeCreationRequest> tournees = new HashSet<>();
        TourneeCreationRequest tourneeCreationRequest = TourneeCreationRequest.builder()
                .reference("testTournee")
                .livraisons(livraisons)
                .employesIds(Set.of())
                .build();
        tournees.add(tourneeCreationRequest);
        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("test")
                .date(new Date(2024,01,04))
                .tournees(tournees)
                .build();

        final  HttpHeaders headers = new HttpHeaders();

        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/journees/create")
                .errorMessage("La commande de référence NotFound est introuvable")
                .build();
        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/journees/create", HttpMethod.POST, new HttpEntity<>(request, headers), NotFoundErrorResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
}

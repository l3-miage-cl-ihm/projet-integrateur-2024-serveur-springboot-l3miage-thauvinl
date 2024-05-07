package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class TourneeControllerTest {
    @Autowired
    TourneeRepository tourneeRepository;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    TestRestTemplate testRestTemplate;
    @AfterEach
    public void clearDataBase(){
        tourneeRepository.deleteAll();
        employeRepository.deleteAll();
    }

    @Test
    public void getTourneeByEmployeFound(){
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        employeRepository.save(employe);
        Set<EmployeEntity> set = new HashSet<>();
        set.add(employe);
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .employeEntitySet(set)
                .build();
        tourneeRepository.save(tournee);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("employeId", "AAA");

        EmployeResponseDTO responseDTO = EmployeResponseDTO.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();

        Set<EmployeResponseDTO> responseSet = new HashSet<>();
        responseSet.add(responseDTO);

        TourneeResponseDTO expected = TourneeResponseDTO.builder()
                .reference("test")
                .employeResponseDTOS(responseSet)
                .montant(0.0)
                .tempsDeMontageTheorique(0)
                .distanceAParcourir(0.0)
                .build();

        ResponseEntity<TourneeResponseDTO> actual = testRestTemplate.exchange("/api/tournees/{employeId}", HttpMethod.GET, new HttpEntity<>(null, headers), TourneeResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    public void testGetTourneeByEmployeNotFoundEmploye(){
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("employeId", "AAA");
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/tournees/AAA")
                .errorMessage("L'employé d'id AAA est introuvable")
                .build();

        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/tournees/{employeId}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void testGetTourneeByEmployeNotFoundTournee(){
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        employeRepository.save(employe);
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("employeId", "AAA");
        NotFoundErrorResponse expected = NotFoundErrorResponse.builder()
                .uri("/api/tournees/AAA")
                .errorMessage("La tournée gérée par l'employé d'id AAA n'a pas été trouvée")
                .build();

        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/tournees/{employeId}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void updateTdmSucces(){
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .tempsDeMontageEffectif(0)
                .build();
        tourneeRepository.save(tournee);

        final HttpHeaders headers = new HttpHeaders();

        final Map<String,Object> urlParams = new HashMap<>();
        urlParams.put("reference", "test");
        urlParams.put("tdmEffectif", 60);

        TourneeResponseDTO expected = TourneeResponseDTO.builder()
                .reference("test")
                .montant(60.0)
                .tempsDeMontageTheorique(0)
                .distanceAParcourir(0.0)
                .build();
        ResponseEntity<TourneeResponseDTO> actual = testRestTemplate.exchange("/api/tournees/{reference}?tdmEffectif={tdmEffectif}", HttpMethod.PATCH, new HttpEntity<>(null, headers), TourneeResponseDTO.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }
}

package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class JourneeControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    TourneeRepository tourneeRepository;

    @AfterEach
    public void clearDatabase() {
        journeeRepository.deleteAll();
        tourneeRepository.deleteAll();
    }

    @Test
    public void getJourneeReturnsJourneeDetails() {
        JourneeEntity journee = new JourneeEntity();
        journee.setReference("J123");
        journeeRepository.save(journee);

        ResponseEntity<JourneeEntity> response = testRestTemplate.getForEntity("/api/journees/{reference}", JourneeEntity.class, journee.getReference());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getReference()).isEqualTo(journee.getReference());
    }

    @Test
    public void getAllJourneeReturnsList(){
        JourneeEntity journee = new JourneeEntity();
        JourneeEntity journee1 = new JourneeEntity();
        journee.setReference("J123");
        journee1.setReference("J456");
        journeeRepository.save(journee);
        journeeRepository.save(journee1);
        ResponseEntity<List> response = testRestTemplate.getForEntity("/api/journees",List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);

    }

    @Test
    public void createJourneeSuccess(){
        JourneeEntity journee = new JourneeEntity();
        journee.setReference("J123");

        ResponseEntity<JourneeEntity> response = testRestTemplate.postForEntity("/api/journees", journee, JourneeEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getReference()).isEqualTo("J123");
        assertThat(journeeRepository.existsByReference(response.getBody().getReference())).isTrue();
    }

    @Test
    public void testGetAllTourneesOfJournee() {
        // Set up the environment
        JourneeEntity journee = new JourneeEntity();
        journee.setReference("J123");
        journeeRepository.save(journee);

        TourneeEntity tournee1 = new TourneeEntity();
        tournee1.setReference("J123-A");
        TourneeEntity tournee2 = new TourneeEntity();
        tournee2.setReference("J123-B");
        tourneeRepository.saveAll(Arrays.asList(tournee1, tournee2));

        // Execute the request
        ResponseEntity<List> response = testRestTemplate.exchange(
                "/api/journees/{reference}/tournees",
                HttpMethod.GET,
                null,
                List.class,
                journee.getReference()
        );

        // Assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }



}

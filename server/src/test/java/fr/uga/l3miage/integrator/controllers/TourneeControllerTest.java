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
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class TourneeControllerTest {
    /*
    @Autowired
    TourneeRepository tourneeRepository;
    @Autowired
    JourneeRepository journeeRepository;
    @Autowired
    TestRestTemplate testRestTemplate;
    @AfterEach
    public void clearDataBase(){
        tourneeRepository.deleteAll();
        journeeRepository.deleteAll();
    }
    @Test
    public void createTourneeDansJourneeSuccess(){
        JourneeEntity journee= new JourneeEntity();
        journee.setReference("J123");
        journeeRepository.save(journee);
        TourneeEntity tournee = new TourneeEntity();
        tournee.setReference("J123");

        // Présumons que les détails de tournee sont configurés correctement ici

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TourneeEntity> request = new HttpEntity<>(tournee, headers);

        ResponseEntity<TourneeEntity> response = testRestTemplate.postForEntity("/api/tournees", request, TourneeEntity.class);

        // Vérifiez le statut de la réponse et l'objet retourné
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // Autres assertions pour vérifier la logique de votre application

    }

    @Test
    public void createTourneeDansJourneeFail(){
        JourneeEntity journee= new JourneeEntity();
        journee.setReference("J123");
        journeeRepository.save(journee);
        TourneeEntity tournee = new TourneeEntity();
        tournee.setReference("J456");

        // Présumons que les détails de tournee sont configurés correctement ici

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TourneeEntity> request = new HttpEntity<>(tournee, headers);

        ResponseEntity<TourneeEntity> response = testRestTemplate.postForEntity("/api/tournees", request, TourneeEntity.class);

        // Vérifiez le statut de la réponse et l'objet retourné
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // Autres assertions pour vérifier la logique de votre application

    }
*/
}

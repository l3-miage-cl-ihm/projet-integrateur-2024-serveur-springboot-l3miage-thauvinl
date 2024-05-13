package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.repositories.ClientRepository;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CommandeControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ClientRepository clientRepository;

    @AfterEach
    public void clearDataBase(){
        clientRepository.deleteAll();
        commandeRepository.deleteAll();
    }

    @BeforeEach
    public void setup(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }


}

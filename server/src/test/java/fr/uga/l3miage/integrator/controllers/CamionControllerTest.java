package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.repositories.CamionRepository;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class CamionControllerTest {
    @Autowired
    CamionRepository camionRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @AfterEach
    public void clearDataBAse(){
        camionRepository.deleteAll();
    }

    @BeforeEach
    public void setup(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }


    @Test
    void testGetAllCamionSuccess(){
        //Given
        CamionEntity camion1 = CamionEntity
                .builder()
                .immatriculation("001")
                .build();
        camionRepository.save(camion1);


        final HttpHeaders headers = new HttpHeaders();

        CamionResponseDTO camionResponseDTO1 = CamionResponseDTO
                .builder()
                .immatriculation("001")
                .build();


        List<CamionResponseDTO> expected = new ArrayList<>();
        expected.add(camionResponseDTO1);

        //When
        ResponseEntity<List<CamionResponseDTO>> actual = testRestTemplate.exchange("/api/camions/AllCamions", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<List<CamionResponseDTO>>() {});

        //Then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

}

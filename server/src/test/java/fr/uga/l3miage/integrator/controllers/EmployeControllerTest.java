package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class EmployeControllerTest {

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @AfterEach
    public void clearDataBase(){
        employeRepository.deleteAll();
    }

    @BeforeEach
    public void setup(){
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void testGetAllLivreursSuccess(){
        EmployeEntity employe1 = EmployeEntity
                .builder()
                .email("test@test.com")
                .trigramme("AAA")
                .emploi(Emploi.livreur)
                .nom("test")
                .prenom("test")
                .build();
        employeRepository.save(employe1);

        final HttpHeaders headers = new HttpHeaders();

        EmployeResponseDTO employeResponseDTO1 = EmployeResponseDTO
                .builder()
                .email("test@test.com")
                .trigramme("AAA")
                .emploi(Emploi.livreur.toString())
                .nom("test")
                .prenom("test")
                .build();


        Set<EmployeResponseDTO> expected = new HashSet<>();
        expected.add(employeResponseDTO1);


        ResponseEntity<Set<EmployeResponseDTO>> actual = testRestTemplate.exchange("/api/employes/livreurs", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<Set<EmployeResponseDTO>>() {});

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void testGetAllEmployesSuccess() {
        // Créer des employés à ajouter à la base de données
        EmployeEntity employe1 = EmployeEntity.builder()
                .email("test1@test.com")
                .trigramme("AAA")
                .emploi(Emploi.livreur)
                .nom("test1")
                .prenom("test1")
                .build();
        EmployeEntity employe2 = EmployeEntity.builder()
                .email("test2@test.com")
                .trigramme("BBB")
                .emploi(Emploi.planificateur)
                .nom("test2")
                .prenom("test2")
                .build();
        employeRepository.saveAll(List.of(employe1, employe2));

        // Envoyer une requête GET pour récupérer tous les employés
        ResponseEntity<List<EmployeResponseDTO>> responseEntity = testRestTemplate.exchange(
                "/api/employes/all", // URL de l'API
                HttpMethod.GET, // Méthode HTTP GET
                null, // Corps de la requête (null dans ce cas)
                new ParameterizedTypeReference<>() {} // Type de réponse attendue
        );

        // Vérifier que le code de statut de la réponse est OK (200)
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Récupérer le corps de la réponse qui contient la liste des employés
        List<EmployeResponseDTO> employes = responseEntity.getBody();

        // Vérifier que la liste des employés n'est pas nulle et qu'elle contient les employés ajoutés
        assertThat(employes).isNotNull();
        assertThat(employes.size() == 2);
    }

    @Test
    void testGetLivreurByEmailSuccess() {
        // Créer un employé livreur à ajouter à la base de données
        EmployeEntity livreur = EmployeEntity.builder()
                .email("livreur@test.com")
                .trigramme("AAA")
                .emploi(Emploi.livreur)
                .nom("Livreur")
                .prenom("Test")
                .build();
        employeRepository.save(livreur);

        EmployeResponseDTO expected = EmployeResponseDTO
                .builder()
                .email("livreur@test.com")
                .trigramme("AAA")
                .emploi(Emploi.livreur.toString())
                .nom("Livreur")
                .prenom("Test")
                .build();

        // Envoyer une requête GET pour récupérer le livreur par son adresse e-mail
        ResponseEntity<EmployeResponseDTO> actual = testRestTemplate.getForEntity(
                "/api/employes/livreurs/{email}", // URL de l'API avec le paramètre email
                EmployeResponseDTO.class, // Type de réponse attendue
                livreur.getEmail() // Valeur du paramètre email
        );

        // Vérifier que le code de statut de la réponse est OK (200)
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    /*
    @Test
    void testGetLivreurByEmailFailed(){
        final HttpHeaders headers = new HttpHeaders();

        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("email", "test@employe.com"); // Utilisez la clé "email" pour correspondre au paramètre dans l'URL

        NotFoundErrorResponse expected = NotFoundErrorResponse
                .builder()
                .uri("/api/employes/livreurs/test@employe.com")
                .errorMessage("L'employé dont le mail est 'test@employe.com' est introuvable")
                .build();

        ResponseEntity<NotFoundErrorResponse> actual = testRestTemplate.exchange("/api/employes/livreurs/{email}", HttpMethod.GET, new HttpEntity<>(null, headers), NotFoundErrorResponse.class, urlParams);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // Vérifiez que le code de statut est 404 NOT_FOUND
        assertThat(actual.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

     */
}

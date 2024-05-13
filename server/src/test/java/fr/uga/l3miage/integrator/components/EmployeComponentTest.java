package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class EmployeComponentTest {

    @Autowired
    private EmployeComponent employeComponent;

    @MockBean
    private EmployeRepository employeRepository;


    @Test
    void testGetAllEmployesSuccess() {
        // Given

        EmployeEntity employe1 = EmployeEntity
                .builder()
                .trigramme("1")
                .emploi(Emploi.livreur)
                .build();

        EmployeEntity employe2 = EmployeEntity
                .builder()
                .trigramme("2")
                .emploi(Emploi.planificateur)
                .build();

        List<EmployeEntity> employes = new ArrayList<>();
        employes.add(employe1);
        employes.add(employe2);

        when(employeRepository.findAll()).thenReturn(employes);


        // When
        List<EmployeEntity> result = employeComponent.getAllEmployes();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(employe1));
        assertTrue(result.contains(employe2));
    }

    @Test
    void testGetAllEmployesEmpty() {
        // Given
        when(employeRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        List<EmployeEntity> result = employeComponent.getAllEmployes();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmployeByIdSuccess() throws NotFoundEmployeEntityException {
        // Given
        String trigramme = "123";
        EmployeEntity expectedEmploye = EmployeEntity.builder()
                .trigramme(trigramme)
                .emploi(Emploi.livreur)
                .build();
        when(employeRepository.findByTrigramme(trigramme)).thenReturn(Optional.of(expectedEmploye));

        // When
        EmployeEntity result = employeComponent.getEmployeById(trigramme);

        // Then
        assertEquals(expectedEmploye, result);
    }

    @Test
    void testGetEmployeByIdNotFound() {
        // Given
        String trigramme = "123";
        when(employeRepository.findByTrigramme(trigramme)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEmployeEntityException.class, () -> employeComponent.getEmployeById(trigramme));
    }

    @Test
    void testGetLivreurByEmailSuccess() throws NotFoundEmployeEntityException {
        // Given
        String email = "test@example.com";
        EmployeEntity expectedEmploye = EmployeEntity.builder()
                .email(email)
                .emploi(Emploi.livreur)
                .build();
        when(employeRepository.findByEmailAndEmploi(email, Emploi.livreur)).thenReturn(Optional.of(expectedEmploye));

        // When
        EmployeEntity result = employeComponent.getLivreurByEmail(email);

        // Then
        assertEquals(expectedEmploye, result);
    }

    @Test
    void testGetLivreurByEmailNotFound() {
        // Given
        String email = "test@example.com";
        when(employeRepository.findByEmailAndEmploi(email, Emploi.livreur)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEmployeEntityException.class, () -> employeComponent.getLivreurByEmail(email));
    }

    @Test
    void testGetAllLivreursSuccess() {
        // Given
        Set<EmployeEntity> expectedLivreurs = new HashSet<>();
        expectedLivreurs.add(EmployeEntity.builder().trigramme("1").emploi(Emploi.livreur).build());
        expectedLivreurs.add(EmployeEntity.builder().trigramme("2").emploi(Emploi.livreur).build());
        when(employeRepository.findAllByEmploi(Emploi.livreur)).thenReturn(expectedLivreurs);

        // When
        Set<EmployeEntity> result = employeComponent.getAllLivreurs();

        // Then
        assertEquals(expectedLivreurs, result);
    }

    @Test
    void testGetAllLivreursEmpty() {
        // Given
        when(employeRepository.findAllByEmploi(Emploi.livreur)).thenReturn(new HashSet<>());

        // When
        Set<EmployeEntity> result = employeComponent.getAllLivreurs();

        // Then
        assertTrue(result.isEmpty());
    }
}

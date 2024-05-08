package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.mappers.EmployeMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class EmployeServiceTest {

    @Autowired
    EmployeService employeService;

    @SpyBean
    EmployeMapper employeMapper;

    @MockBean
    EmployeComponent employeComponent;

    @Test
    void testGetAllEmployesSuccess(){
        //Given
        EmployeEntity employe1 = EmployeEntity
                .builder().trigramme("1").build();

        EmployeEntity employe2 = EmployeEntity
                .builder().trigramme("2").build();

        List<EmployeEntity> employes = Arrays.asList(employe1, employe2);

        EmployeResponseDTO employeResponseDTO1 = EmployeResponseDTO.builder().build();
        EmployeResponseDTO employeResponseDTO2 = EmployeResponseDTO.builder().build();

        when(employeComponent.getAllEmployes()).thenReturn(employes);
        when(employeMapper.toResponse(employe1)).thenReturn(employeResponseDTO1);
        when(employeMapper.toResponse(employe2)).thenReturn(employeResponseDTO2);

        //When
        List<EmployeResponseDTO> result = employeService.getAllEmployes();

        //Then
        assertEquals(2, result.size());
        assertEquals(employeResponseDTO1,result.get(0));
        assertEquals(employeResponseDTO2,result.get(1));
    }

    @Test
    void testGetAllEmployesEmpty() {
        // Given
        when(employeComponent.getAllEmployes()).thenReturn(new ArrayList<>());

        // When
        List<EmployeResponseDTO> result = employeService.getAllEmployes();

        // Then
        assertEquals(0, result.size());
    }

    @Test
    void testGetAllLivreursSuccess() {
        // Given
        EmployeEntity livreur1 = EmployeEntity.builder().trigramme("1").build();
        EmployeEntity livreur2 = EmployeEntity.builder().trigramme("2").build();

        Set<EmployeEntity> livreurs = new HashSet<>();
        livreurs.add(livreur1);
        livreurs.add(livreur2);

        EmployeResponseDTO employeResponseDTO1 = EmployeResponseDTO.builder().trigramme("1").emploi(Emploi.livreur.toString()).build();
        EmployeResponseDTO employeResponseDTO2 = EmployeResponseDTO.builder().trigramme("2").emploi(Emploi.livreur.toString()).build();

        when(employeComponent.getAllLivreurs()).thenReturn(livreurs);
        when(employeMapper.toResponse(livreur1)).thenReturn(employeResponseDTO1);
        when(employeMapper.toResponse(livreur2)).thenReturn(employeResponseDTO2);

        // When
        Set<EmployeResponseDTO> result = employeService.getAllLivreurs();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(employeResponseDTO1));
        assertTrue(result.contains(employeResponseDTO2));
    }

    @Test
    void testGetAllLivreursEmpty() {
        // Given
        when(employeComponent.getAllLivreurs()).thenReturn(new HashSet<>());

        // When
        Set<EmployeResponseDTO> result = employeService.getAllLivreurs();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetLivreurByEmailSuccess() throws NotFoundEmployeEntityException {
        // Given
        String email = "test@example.com";
        EmployeEntity livreur = EmployeEntity.builder()
                .email(email)
                .build();
        EmployeResponseDTO expectedResponse = EmployeResponseDTO.builder()
                .email(email)
                .build();

        when(employeComponent.getLivreurByEmail(email)).thenReturn(livreur);
        when(employeMapper.toResponse(livreur)).thenReturn(expectedResponse);

        // When
        EmployeResponseDTO result = employeService.getLivreurByEmail(email);

        // Then
        assertEquals(expectedResponse, result);
    }

    @Test
    void testGetLivreurByEmailNotFound() throws NotFoundEmployeEntityException {
        // Given
        String email = "test@example.com";

        when(employeComponent.getLivreurByEmail(email)).thenThrow(NotFoundEmployeEntityException.class);

        // When
        EmployeResponseDTO result = employeService.getLivreurByEmail(email);

        // Then
        assertNull(result);
    }

}

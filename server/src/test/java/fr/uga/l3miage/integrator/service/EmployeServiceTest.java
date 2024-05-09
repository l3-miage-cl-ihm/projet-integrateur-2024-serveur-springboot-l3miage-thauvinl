package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.mappers.EmployeMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import fr.uga.l3miage.integrator.services.EmployeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeServiceTest {

    @MockBean
    private EmployeComponent employeComponent;

    @SpyBean
    private EmployeMapper employeMapper;

    @Autowired
    private EmployeService employeService;

    @Test
    void testGetAllEmployesSuccess() {
        // Given
        EmployeEntity employeEntity1 = EmployeEntity
                .builder().build();
        EmployeEntity employeEntity2 = EmployeEntity
                .builder().build();
        List<EmployeEntity> employes = List.of(employeEntity1, employeEntity2);

        EmployeResponseDTO employeResponseDTO1 =  EmployeResponseDTO
                .builder().build();
        EmployeResponseDTO employeResponseDTO2 =  EmployeResponseDTO
                .builder().build();
        when(employeComponent.getAllEmployes()).thenReturn(employes);
        when(employeMapper.toResponse(employeEntity1)).thenReturn(employeResponseDTO1);
        when(employeMapper.toResponse(employeEntity2)).thenReturn(employeResponseDTO2);

        // When
        List<EmployeResponseDTO> result = employeService.getAllEmployes();

        // Then
        assertEquals(2, result.size());
        assertEquals(employeResponseDTO1, result.get(0));
        assertEquals(employeResponseDTO2, result.get(1));
    }

    @Test
    public void testGetAllEmployesFail() {
        // Given
        when(employeComponent.getAllEmployes()).thenReturn(Collections.emptyList());

        // When
        List<EmployeResponseDTO> result = employeService.getAllEmployes();

        // Then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetAllLivreursSuccess() {
        // Given

        EmployeEntity employe1 = EmployeEntity
                .builder()
                .email("maria@hiiii.com")
                .emploi(Emploi.livreur)
                .build();

        EmployeEntity employe2 = EmployeEntity
                .builder()
                .email("maria@hoooooo.com")
                .emploi(Emploi.livreur)
                .build();

        Set<EmployeEntity> employes = new HashSet<>();
        employes.add(employe1);
        employes.add(employe2);
        when(employeComponent.getAllLivreurs()).thenReturn(employes);

        // When
        Set<EmployeResponseDTO> result = employeService.getAllLivreurs();

        // Then
        assertEquals(2, result.size());
        // Add more assertions if needed
    }

    @Test
    public void testGetAllLivreursFailure() {
        // Given
        when(employeComponent.getAllLivreurs()).thenReturn(Collections.emptySet());

        // When
        Set<EmployeResponseDTO> result = employeService.getAllLivreurs();

        // Then
        assertEquals(Collections.emptySet(), result);
    }

    @Test
    public void testGetLivreurByEmailSuccess() throws NotFoundEmployeEntityException {
        // Given
        String email = "livreur@example.com";
        /*
        EmployeEntity mockLivreurEntity = new EmployeEntity(email);
        EmployeResponseDTO expectedResponse = new EmployeResponseDTO(email);
    */
        EmployeEntity employe = EmployeEntity
                .builder()
                .email(email)
                .emploi(Emploi.livreur)
                .build();

        EmployeResponseDTO expectedResponse = EmployeResponseDTO
                .builder()
                .emploi("livreur")
                .email(email)
                .build();

        when(employeComponent.getLivreurByEmail(email)).thenReturn(employe);
        when(employeMapper.toResponse(employe)).thenReturn(expectedResponse);

        // When
        EmployeResponseDTO result = employeService.getLivreurByEmail(email);

        // Then
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetLivreurByEmailFailure() throws NotFoundEmployeEntityException {
        // Given
        String email = "nonexistent@example.com";

        when(employeComponent.getLivreurByEmail(email)).thenThrow(NotFoundEmployeEntityException.class);

        // When, Then
        assertThrows(NotFoundEntityRestException.class, () -> employeService.getLivreurByEmail(email));
    }
}

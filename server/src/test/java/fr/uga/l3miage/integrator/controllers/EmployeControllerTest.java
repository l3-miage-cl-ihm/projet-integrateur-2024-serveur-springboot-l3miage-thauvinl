package fr.uga.l3miage.integrator.controllers;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class EmployeControllerTest {

    @Mock
    private EmployeService employeService;

    @InjectMocks
    private EmployeController employeController;

    @BeforeEach
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLivreursByTourneeIdSucces() throws NotFoundException {
        // Given
        String tourneeId = "123";

        // Créer un ensemble d'employés simulé
        EmployeEntity employe1 = EmployeEntity
                .builder()
                .email("test1")
                .nom("titi")
                .emploi(Emploi.livreur)
                .build();

        EmployeEntity employe2 = EmployeEntity
                .builder()
                .email("test2")
                .nom("toto")
                .emploi(Emploi.planificateur)
                .build();

        Set<EmployeEntity> employes = new HashSet<>();
        employes.add(employe1);
        employes.add(employe2);

        // Définir le comportement simulé du service
        when(employeService.getLivreursByTourneeId(tourneeId)).thenReturn(employes);

        // When
        ResponseEntity<Set<EmployeEntity>> responseEntity = employeController.getLivreursByTourneeId(tourneeId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employes, responseEntity.getBody());
    }

    /*@Test
    public void testGetLivreursByTourneeIdFail() throws NotFoundException {
        // Given
        String tourneeId = "123";

        // Définir le comportement simulé du service pour lancer une exception NotFoundException
        doThrow(NotFoundException.class).when(employeService).getLivreursByTourneeId(tourneeId);

        // When
        ResponseEntity<Set<EmployeEntity>> responseEntity = employeController.getLivreursByTourneeId(tourneeId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }*/

}

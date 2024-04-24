package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.services.EmployeService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class EmployeServiceTest {

    @Mock
    private EmployeComponent employeComponent;

    @Mock
    private EmployeRepository employeRepository;

    @InjectMocks
    private EmployeService employeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLivreursByTourneeIdSuccess() throws NotFoundException {
        // Given
        String tourneeId = "123";

        // Create a set of mock employees
        EmployeEntity employe1 = new EmployeEntity();
        employe1.setEmail("test1");
        employe1.setNom("titi");

        EmployeEntity employe2 = new EmployeEntity();
        employe2.setEmail("test2");
        employe2.setNom("toto");

        Set<EmployeEntity> employes = new HashSet<>();
        employes.add(employe1);
        employes.add(employe2);

        // Set up mock behavior
        when(employeComponent.getLivreursByTourneeId(tourneeId)).thenReturn(employes);

        // When
        Set<EmployeEntity> result = employeService.getLivreursByTourneeId(tourneeId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(employe1));
        assertTrue(result.contains(employe2));
    }

    @Test
    public void testGetLivreursByTourneeIdFail() throws NotFoundException {
        // Given
        String tourneeId = "456";

        // Set up mock behavior to throw NotFoundException
        when(employeComponent.getLivreursByTourneeId(tourneeId)).thenThrow(new NotFoundException("Tournée non trouvée"));

        // When, Then
        assertThrows(NotFoundException.class, () -> employeService.getLivreursByTourneeId(tourneeId));
    }

}

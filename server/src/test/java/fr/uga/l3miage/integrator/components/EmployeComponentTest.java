package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeComponentTest {
/*
    @Mock
    private TourneeRepository tourneeRepository;

    @InjectMocks
    private EmployeComponent employeComponent;

    @Test
    public void testGetLivreursByTourneeIdWithValidTourneeId() throws NotFoundException {
        // Given
        String tourneeId = "T-01";

        TourneeEntity tournee = new TourneeEntity();
        EmployeEntity livreur1 = new EmployeEntity();
        livreur1.setTrigramme("ABC");
        livreur1.setEmploi(Emploi.livreur);
        EmployeEntity employeOrdinaire = new EmployeEntity();
        employeOrdinaire.setTrigramme("DEF");
        employeOrdinaire.setEmploi(Emploi.planificateur);
        Set<EmployeEntity> employes = new HashSet<>();
        employes.add(livreur1);
        employes.add(employeOrdinaire);
        tournee.setEmployeEntitySet(employes);

        when(tourneeRepository.findById(tourneeId)).thenReturn(Optional.of(tournee));

        // When
        Set<EmployeEntity> result = employeComponent.getLivreursByTourneeId(tourneeId);

        // Then
        assertEquals(1, result.size());
        EmployeEntity premierEmploye = result.iterator().next();
        assertEquals(Emploi.livreur, premierEmploye.getEmploi());
    }

    @Test
    public void testGetLivreursByTourneeIdWithInvalidTourneeId() {
        // Given
        String invalidTourneeId = "Invalid-ID";

        when(tourneeRepository.findById(invalidTourneeId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> employeComponent.getLivreursByTourneeId(invalidTourneeId));
    }
}
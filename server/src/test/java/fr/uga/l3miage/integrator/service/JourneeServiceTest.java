package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JourneeServiceTest {
    @Autowired
    private JourneeService journeeService;

    @MockBean
    private JourneeRepository journeeRepository;

    @MockBean
    private TourneeRepository tourneeRepository;

    @Test
    void findJourneeByReference(){
        JourneeEntity journee = new JourneeEntity();
        when(journeeRepository.findByReference("ref123")).thenReturn(Optional.of(journee));
        Optional<JourneeEntity> found = journeeService.findJourneeByReference("ref123");
        assertTrue(found.isPresent());
        assertEquals(journee, found.get());
    }

    @Test
    public void testFindAllJournees() {
        List<JourneeEntity> list = new ArrayList<>();
        when(journeeRepository.findAll()).thenReturn(list);
        List<JourneeEntity> result = journeeService.findAllJournees();
        assertNotNull(result);
    }

    @Test
    public void testCreateJournee() {
        JourneeEntity journee = new JourneeEntity();
        when(journeeRepository.save(journee)).thenReturn(journee);
        JourneeEntity created = journeeService.createJournee(journee);
        assertNotNull(created);
    }

    @Test
    public void testGetAllTourneesOfJournee() {
        String reference = "ref123";
        List<TourneeEntity> expectedTournees = new ArrayList<>();
        when(tourneeRepository.findByReferenceContaining(reference)).thenReturn(expectedTournees);
        List<TourneeEntity> tournees = journeeService.getAllTourneesOfJournee(reference);
        assertEquals(expectedTournees, tournees);
    }
}

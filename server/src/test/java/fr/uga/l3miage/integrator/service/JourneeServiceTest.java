package fr.uga.l3miage.integrator.service;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JourneeServiceTest {
/*
    @Mock
    private JourneeComponent journeeComponent;

    @InjectMocks
    private JourneeService journeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindJourneeByReference() {
        // Mocking
        String reference = "ref-123";
        JourneeEntity expectedJournee = new JourneeEntity();
        when(journeeComponent.findJourneeByReference(reference)).thenReturn(Optional.of(expectedJournee));

        // Test
        Optional<JourneeEntity> journee = journeeService.findJourneeByReference(reference);

        // Assertion
        assertEquals(expectedJournee, journee.orElse(null));
    }

    @Test
    void testFindAllJournees() {
        // Mocking
        List<JourneeEntity> expectedJournees = new ArrayList<>();
        when(journeeComponent.findAllJournees()).thenReturn(expectedJournees);

        // Test
        List<JourneeEntity> journees = journeeService.findAllJournees();

        // Assertion
        assertEquals(expectedJournees, journees);
    }

    @Test
    void testCreateJournee() {
        // Mocking
        JourneeEntity journeeToCreate = new JourneeEntity();
        when(journeeComponent.createJournee(any())).thenReturn(journeeToCreate);

        // Test
        JourneeEntity createdJournee = journeeService.createJournee(journeeToCreate);

        // Assertion
        assertEquals(journeeToCreate, createdJournee);
    }

    @Test
    void testGetAllTourneesOfJournee() {
        // Mocking
        String reference = "ref-456";
        List<TourneeEntity> expectedTournees = new ArrayList<>();
        when(journeeComponent.getAllTourneesOfJournee(reference)).thenReturn(expectedTournees);

        // Test
        List<TourneeEntity> tournees = journeeService.getAllTourneesOfJournee(reference);

        // Assertion
        assertEquals(expectedTournees, tournees);
    }
    /* TEST pour component probablement
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

 */
}

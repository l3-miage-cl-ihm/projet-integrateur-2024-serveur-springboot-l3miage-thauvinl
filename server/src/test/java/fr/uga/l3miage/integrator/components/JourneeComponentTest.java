package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class JourneeComponentTest {

    @Autowired
    JourneeComponent journeeComponent;

    @MockBean
    JourneeRepository journeeRepository;

    @Test
    void getJourneeByRefSucces(){
        JourneeEntity journee = JourneeEntity.builder()
                .reference("Test")
                .date(new Date())
                .build();
        when(journeeRepository.findByReference(any(String.class))).thenReturn(Optional.of(journee));
        assertDoesNotThrow(()->journeeComponent.getJourneeByRef("Test"));
    }

    @Test
    void getJourneeNotFound(){
        when(journeeRepository.findByReference(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundJourneeEntityException.class, ()->journeeComponent.getJourneeByRef("Test"));
    }
}

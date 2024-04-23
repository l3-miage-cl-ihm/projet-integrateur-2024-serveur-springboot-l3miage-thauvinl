package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JourneeComponentTest {

    @Autowired
    JourneeComponent journeeComponent;

    @MockBean
    JourneeRepository journeeRepository;

    @Test
    void getJourneeFound(){
        JourneeEntity journee = JourneeEntity.builder()
                .reference("Test")
                .tournees(Set.of())
                .build();
        when(journeeRepository.findByReference(any(String.class))).thenReturn(Optional.of(journee));
        assertDoesNotThrow(()->journeeComponent.getJournee("Test"));
    }

    @Test
    void getJourneeNotFound(){
        when(journeeRepository.findByReference(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundJourneeEntityException.class, ()->journeeComponent.getJournee("Test"));
    }
}

package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JourneeRepositoryTest {

    @Autowired
    private JourneeRepository journeeRepository;

    @Test
    void findJourneeByReference(){
        JourneeEntity journee = JourneeEntity.builder()
                .reference("Test")
                .build();
        journeeRepository.save(journee);

        Optional<JourneeEntity> response = journeeRepository.findByReference("Test");
        Optional<JourneeEntity> vide = journeeRepository.findByReference("NonExistant");
        assertThat(response.get().getReference()).isEqualTo("Test");
        assertThat(vide).isEmpty();
    }
}

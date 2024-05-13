package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.repositories.CamionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class CamionRepositoryTest {

    @Autowired
    private CamionRepository camionRepository;

    @Test
    void testFindCamionEntityByImmatriculation() {
        // Given
        CamionEntity camion = CamionEntity.builder()
                .immatriculation("1234ABCD")
                .build();

        camionRepository.save(camion);

        // When
        Optional<CamionEntity> foundCamionOptional = camionRepository.findCamionEntityByImmatriculation("1234ABCD");

        // Then
        assertThat(foundCamionOptional).isPresent();
        CamionEntity foundCamion = foundCamionOptional.get();
        assertThat(foundCamion.getImmatriculation()).isEqualTo("1234ABCD");
    }
}
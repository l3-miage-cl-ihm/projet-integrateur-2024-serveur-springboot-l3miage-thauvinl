package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Test
    void testFindAllByEmploi() {
        // Given
        EmployeEntity employe1 = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test1@test.fr")
                .emploi(Emploi.livreur)
                .build();

        EmployeEntity employe2 = EmployeEntity.builder()
                .trigramme("BBB")
                .email("test2@test.fr")
                .emploi(Emploi.planificateur)
                .build();

        employeRepository.saveAll(Set.of(employe1, employe2));

        // When
        Set<EmployeEntity> livreursEmployes = employeRepository.findAllByEmploi(Emploi.livreur);
        Set<EmployeEntity> planificateursEmployes = employeRepository.findAllByEmploi(Emploi.planificateur);

        // Then
        assertThat(livreursEmployes.size()).isEqualTo(1);
        assertThat(livreursEmployes.stream().map(EmployeEntity::getTrigramme)).contains(employe1.getTrigramme());
        assertThat(planificateursEmployes.size()).isEqualTo(1);
        assertThat(planificateursEmployes.stream().map(EmployeEntity::getTrigramme)).contains(employe2.getTrigramme());
    }

    @Test
    void testFindByTrigramme() {
        // Given
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .emploi(Emploi.livreur)
                .build();

        employeRepository.save(employe);

        // When
        Optional<EmployeEntity> foundEmployeOptional = employeRepository.findByTrigramme("AAA");

        // Then
        assertThat(foundEmployeOptional).isPresent();
        EmployeEntity foundEmploye = foundEmployeOptional.get();
        assertThat(foundEmploye.getTrigramme()).isEqualTo("AAA");
        assertThat(foundEmploye.getEmail()).isEqualTo("test@test.fr");
        assertThat(foundEmploye.getEmploi()).isEqualTo(Emploi.livreur);
    }


}

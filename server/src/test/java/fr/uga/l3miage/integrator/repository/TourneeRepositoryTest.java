package fr.uga.l3miage.integrator.repository;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class TourneeRepositoryTest {
    /*

    @Autowired
    private TourneeRepository tourneeRepository;

    @Test
    void testFindTourneeByReference(){
        TourneeEntity tournee =  TourneeEntity.builder()
                .reference("test")
                .build();
        tourneeRepository.save(tournee);
        Optional<TourneeEntity> response = tourneeRepository.findByReference("test");
        Optional<TourneeEntity> exception = tourneeRepository.findByReference("exceptionNotFound");
        assertThat(response.get().getReference()).isEqualTo("test");
        assertThat(exception.isPresent()).isFalse();
    }

    @Test
    void testFindByEmployeEntitySetContains(){
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .emploi(Emploi.livreur)
                .build();
        Set<EmployeEntity> set = new HashSet<>();
        set.add(employe);
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .employeEntitySet(set)
                .build();
        Optional<TourneeEntity> response = tourneeRepository.findByEmployeEntitySetContains(employe);
        assertThat(response.get().getEmployeEntitySet().size()).isEqualTo(1);

    }

     */
}

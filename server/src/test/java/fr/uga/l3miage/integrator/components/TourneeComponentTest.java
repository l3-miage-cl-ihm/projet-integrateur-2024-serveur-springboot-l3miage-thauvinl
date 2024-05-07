package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TourneeComponentTest {

    @Autowired
    TourneeComponent tourneeComponent;

    @MockBean
    TourneeRepository tourneeRepository;

    @MockBean
    EmployeRepository employeRepository;

    @Test
    void getTourneeByEmployeTest() throws NotFoundEmployeEntityException, NotFoundTourneeEntityException {

        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .employeEntitySet(Set.of())
                .build();

        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        when(employeRepository.findByTrigramme(any(String.class))).thenReturn(Optional.of(employe));
        when(tourneeRepository.findByEmployeEntitySetContains(employe)).thenReturn(Optional.of(tournee));

        TourneeEntity testTournee = tourneeComponent.getTourneeByEmploye("AAA");


    }
}

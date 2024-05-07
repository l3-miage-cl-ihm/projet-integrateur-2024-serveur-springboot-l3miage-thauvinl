package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeTournee;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class TourneeComponentTest {

    @Autowired
    TourneeComponent tourneeComponent;

    @MockBean
    TourneeRepository tourneeRepository;

    @MockBean
    EmployeRepository employeRepository;

    @Test
    void getTourneeByEmployeFound() throws NotFoundEmployeEntityException, NotFoundTourneeEntityException {

        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        Set<EmployeEntity> employeEntities = new HashSet<>();
        employeEntities.add(employe);

        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .employeEntitySet(employeEntities)
                .build();


        when(employeRepository.findByTrigramme(any(String.class))).thenReturn(Optional.of(employe));
        when(tourneeRepository.findByEmployeEntitySetContains(any(EmployeEntity.class))).thenReturn(Optional.of(tournee));

        TourneeEntity testTournee = tourneeComponent.getTourneeByEmploye("AAA");
        assertThat(testTournee).isEqualTo(tournee);
    }

    @Test
    void getTourneeByEmployeNotFoundEmploye(){
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .build();
        when(employeRepository.findByTrigramme(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundEmployeEntityException.class, () -> tourneeComponent.getTourneeByEmploye("AAA"));
    }

    @Test
    public void getTourneeByEmployeNotFound(){
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        when(employeRepository.findByTrigramme(any(String.class))).thenReturn(Optional.of(employe));
        when(tourneeRepository.findByEmployeEntitySetContains(any(EmployeEntity.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundTourneeEntityException.class, () -> tourneeComponent.getTourneeByEmploye("AAA"));
    }

    @Test
    public void updateTdmSucces() throws NotFoundTourneeEntityException {
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .tempsDeMontageEffectif(0)
                .build();
        Integer tdmEffectif = 60;
        when(tourneeRepository.findByReference(any(String.class))).thenReturn(Optional.of(tournee));
        when(tourneeRepository.save(any(TourneeEntity.class))).thenReturn(tournee);
        TourneeEntity response = tourneeComponent.updateTdm("test", tdmEffectif);
        assertThat(response.getReference()).isEqualTo("test");
        assertThat(response.getTempsDeMontageEffectif()).isEqualTo(tdmEffectif);
    }

    @Test
    public void updateTdmNotFound(){
        Integer tdmEffectif = 60;
        when(tourneeRepository.findByReference(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundTourneeEntityException.class, () -> tourneeComponent.updateTdm("test", tdmEffectif));
    }

    @Test
    public void updateTdmIllegalArgument(){
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .tempsDeMontageEffectif(0)
                .build();
        Integer tdmEffectif = -20;
        when(tourneeRepository.findByReference(any(String.class))).thenReturn(Optional.of(tournee));
        when(tourneeRepository.save(any(TourneeEntity.class))).thenReturn(tournee);
        assertThrows(ResponseStatusException.class, () -> tourneeComponent.updateTdm("test", tdmEffectif));
    }

    @Test
    public void updateEtatSucces() throws NotFoundTourneeEntityException {
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .etatsDeTournee(EtatDeTournee.enChargement)
                .build();
        String nouvelEtat = "effectuee";
        when(tourneeRepository.findByReference(any(String.class))).thenReturn(Optional.of(tournee));
        when(tourneeRepository.save(any(TourneeEntity.class))).thenReturn(tournee);
        TourneeEntity response = tourneeComponent.updateEtat("test", nouvelEtat);
        assertThat(response.getReference()).isEqualTo("test");
        assertThat(response.getEtatsDeTournee()).isEqualTo(EtatDeTournee.valueOf(nouvelEtat));
    }

    @Test
    public void updateEtatNotFound(){
        String nouvelEtat = "effectuee";
        when(tourneeRepository.findByReference(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundTourneeEntityException.class, () -> tourneeComponent.updateEtat("test", nouvelEtat));
    }
}

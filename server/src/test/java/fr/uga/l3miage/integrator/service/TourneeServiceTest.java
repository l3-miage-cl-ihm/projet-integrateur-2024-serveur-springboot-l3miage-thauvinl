package fr.uga.l3miage.integrator.service;

import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.TourneeMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeTournee;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import fr.uga.l3miage.integrator.services.TourneeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class TourneeServiceTest {

    @Autowired
    TourneeService tourneeService;

    @SpyBean
    TourneeMapper tourneeMapper;

    @MockBean
    TourneeComponent tourneeComponent;

    @Test
    public void getTourneeByEmployeTest() throws NotFoundEmployeEntityException, NotFoundTourneeEntityException {
        EmployeEntity employe = EmployeEntity.builder()
                .trigramme("AAA")
                .email("test@test.fr")
                .build();
        Set<EmployeEntity> set = new HashSet<>();
        set.add(employe);
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .employeEntitySet(set)
                .build();
        when(tourneeComponent.getTourneeByEmploye(any(String.class))).thenReturn(tournee);
        TourneeResponseDTO expected = tourneeMapper.toResponse(tournee);
        TourneeResponseDTO actual = tourneeService.getTourneeByEmploye("AAA");
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(tourneeMapper, times(2)).toResponse(same(tournee));
    }

    @Test
    public void getTourneeByEmployeTestNotFoundEmploye() throws NotFoundEmployeEntityException, NotFoundTourneeEntityException {
        when(tourneeComponent.getTourneeByEmploye(any(String.class))).thenThrow(NotFoundEmployeEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> tourneeService.getTourneeByEmploye("AAA"));
    }

    @Test
    public void getTourneeByEmployeTestNotFoundTournee() throws NotFoundEmployeEntityException, NotFoundTourneeEntityException {
        when(tourneeComponent.getTourneeByEmploye(any(String.class))).thenThrow(NotFoundTourneeEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> tourneeService.getTourneeByEmploye("AAA"));
    }

    @Test
    public void updateEtatTest() throws NotFoundTourneeEntityException {
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .etatsDeTournee(EtatDeTournee.enChargement)
                .build();
        String nouvelEtat = "planifiee";
        when(tourneeComponent.updateEtat(any(String.class), any(String.class))).thenReturn(tournee);
        tournee.setEtatsDeTournee(EtatDeTournee.valueOf(nouvelEtat));
        TourneeResponseDTO expected = tourneeMapper.toResponse(tournee);
        TourneeResponseDTO actual = tourneeService.updateEtat("test", nouvelEtat);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void updateEtatTourneeNotFound() throws NotFoundTourneeEntityException {
        when(tourneeComponent.updateEtat(any(String.class), any(String.class))).thenThrow(NotFoundTourneeEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> tourneeService.updateEtat("test", "planifiee"));
    }

    @Test
    public void updateTdmTest() throws NotFoundTourneeEntityException {
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("test")
                .tempsDeMontageEffectif(0)
                .build();
        Integer nouveauTdm = 60;
        when(tourneeComponent.updateTdm(any(String.class), any(Integer.class))).thenReturn(tournee);
        tournee.setTempsDeMontageEffectif(nouveauTdm);
        TourneeResponseDTO expected = tourneeMapper.toResponse(tournee);
        TourneeResponseDTO actual = tourneeService.updateTdmEffectifTournee("test", nouveauTdm);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void updateTdmTourneeNotFound() throws NotFoundTourneeEntityException {
        when(tourneeComponent.updateTdm(any(String.class), any(Integer.class))).thenThrow(NotFoundTourneeEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> tourneeService.updateTdmEffectifTournee("test", 60));
    }
}

package fr.uga.l3miage.integrator.service;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.AddingTourneeRestException;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.JourneeMapper;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JourneeServiceTest {
    @Autowired
    private JourneeService journeeService;

    @MockBean
    private JourneeComponent journeeComponent;

    @MockBean
    private TourneeComponent tourneeComponent;

    @SpyBean
    private JourneeMapper journeeMapper;

    @Test
    void createJournee(){
        //given
        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .date(new Date(2024,4,15))
                .distanceAParcourir(150.0)
                .montant(800.3)
                .reference("Test")
                .tempsDeMontageTheorique(150)
                .tournees(Set.of())
                .build();

        JourneeEntity journeeEntity = journeeMapper.toEntity(request);
        journeeEntity.setTournees(Set.of());

        when(journeeComponent.createJournee(any(JourneeEntity.class))).thenReturn(journeeEntity);
        JourneeResponseDTO expected = journeeMapper.toResponseWithTournees(journeeEntity);
        JourneeResponseDTO actual = journeeService.createJournee(request);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(journeeMapper, times(2)).toEntity(request);
        verify(journeeMapper, times(2)).toResponseWithTournees(journeeEntity);
        verify(journeeComponent, times(1)).createJournee(any(JourneeEntity.class));
    }

    @Test
    void getJourneeFound() throws NotFoundJourneeEntityException {
        JourneeEntity journeeEntity = JourneeEntity.builder()
                .reference("Test")
                .date(new Date(2024,4,15))
                .distanceAParcourir(150.0)
                .montant(800.3)
                .tempsDeMontageTheorique(150)
                .tournees(Set.of())
                .build();
        when(journeeComponent.getJournee(any(String.class))).thenReturn(journeeEntity);
        JourneeResponseDTO expected = journeeMapper.toResponseWithTournees(journeeEntity);
        JourneeResponseDTO actual = journeeService.getJournee("Test");
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(journeeMapper,times(2)).toResponseWithTournees(journeeEntity);
        verify(journeeComponent, times(1)).getJournee(any(String.class));
    }

    @Test
    void getJourneeNotFound() throws NotFoundJourneeEntityException{
        when(journeeComponent.getJournee(any(String.class))).thenThrow(new NotFoundJourneeEntityException("Journée introuvable"));
        assertThrows(NotFoundEntityRestException.class, ()-> journeeService.getJournee("Test"));
    }

    @Test
    void addTourneeInJourneeSuccess() throws NotFoundJourneeEntityException, NotFoundTourneeEntityException{
        JourneeEntity journee = JourneeEntity.builder()
                .reference("Test")
                .date(new Date(2024,4,15))
                .distanceAParcourir(150.0)
                .montant(800.3)
                .tempsDeMontageTheorique(150)
                .tournees(new HashSet<>())
                .build();
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("T001")
                .build();
        journee.getTournees().add(tournee);
        journeeComponent.createJournee(journee);

        when(tourneeComponent.getTourneeByRef(any(String.class))).thenReturn(tournee);
        when(journeeComponent.addTourneeInJournee(any(String.class), any(TourneeEntity.class))).thenReturn(journee);

        JourneeResponseDTO expected = journeeMapper.toResponseWithTournees(journee);
        JourneeResponseDTO actual = journeeService.addTourneeInJournee("Test", "T001");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(journeeMapper, times(2)).toResponseWithTournees(journee);
        verify(journeeComponent, times(1)).addTourneeInJournee(any(String.class), any(TourneeEntity.class));
    }

    @Test
    void addTourneeInJourneeFailedJourneeNotFound() throws NotFoundJourneeEntityException, NotFoundTourneeEntityException{
        TourneeEntity tournee = TourneeEntity.builder()
                .reference("T001")
                .build();

        when(tourneeComponent.getTourneeByRef(any(String.class))).thenReturn(tournee);
        when(journeeComponent.getJournee(any(String.class))).thenThrow(new NotFoundJourneeEntityException("La journée est introuvable avec la référence donnée"));
        when(journeeComponent.addTourneeInJournee(any(String.class), any(TourneeEntity.class))).thenThrow(new AddingTourneeRestException("Impossible d'ajouter, journée introuvable"));

        assertThrows(AddingTourneeRestException.class, ()-> journeeService.addTourneeInJournee("TestNotFoundJournée", "T001"));
    }

    @Test
    void addTourneeInJourneeeFailedTourneeNotFound() throws NotFoundJourneeEntityException, NotFoundTourneeEntityException{
        JourneeEntity journee = JourneeEntity.builder()
                .reference("Test")
                .date(new Date(2024,4,15))
                .distanceAParcourir(150.0)
                .montant(800.3)
                .tempsDeMontageTheorique(150)
                .tournees(new HashSet<>())
                .build();
        when(tourneeComponent.getTourneeByRef(any(String.class))).thenThrow(new NotFoundTourneeEntityException("La tournée est introuvable avec la référence donnée"));
        when(journeeComponent.addTourneeInJournee(any(String.class), any(TourneeEntity.class))).thenThrow(new AddingTourneeRestException("Impossible d'ajouter, tournée introuvable"));

        assertThrows(AddingTourneeRestException.class, ()->journeeService.addTourneeInJournee("Test", "TestNotFound"));
    }

}*/

package fr.uga.l3miage.integrator.service;
import fr.uga.l3miage.integrator.components.JourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.mappers.JourneeMapper;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.services.JourneeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class JourneeServiceTest {

    @Autowired
    private JourneeService journeeService;

    @MockBean
    private JourneeComponent journeeComponent;

    @SpyBean
    private JourneeMapper journeeMapper;


    @Test
    public void getJourneeByRefSuccess() throws NotFoundJourneeEntityException {
        JourneeEntity journee = JourneeEntity.builder()
                .date(new Date())
                .reference("test")
                .build();

        when(journeeComponent.getJourneeByRef(any(String.class))).thenReturn(journee);
        JourneeResponseDTO expected = journeeMapper.toResponseWithTournees(journee);
        JourneeResponseDTO actual = journeeService.getJournee("test");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(journeeMapper, times(2)).toResponseWithTournees(same(journee));
    }

    @Test
    public void getJourneeByRefShouldThrowNotFoundEntityRestException() throws NotFoundJourneeEntityException {
        when(journeeComponent.getJourneeByRef(any(String.class))).thenThrow(NotFoundJourneeEntityException.class);
        assertThrows(NotFoundEntityRestException.class, () -> journeeService.getJournee("test"));
    }

    @Test
    public void createJourneeSuccess(){
        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("test")
                .date(new Date())
                .tournees(Set.of())
                .build();
        JourneeEntity journee = JourneeEntity.builder()
                .reference("test")
                .date(new Date())
                .tournees(Set.of())
                .build();
        when(journeeComponent.createJournee(any(JourneeEntity.class))).thenReturn(journee);
        JourneeResponseDTO expected = journeeMapper.toResponseWithTournees(journee);
        JourneeResponseDTO actual = journeeService.createJournee(request);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(journeeMapper, times(1)).toEntity(same(request));
        verify(journeeMapper, times(2)).toResponseWithTournees(same(journee));
    }
    @Test
    public void createJourneeShouldThrowBadRequestRestException(){
        JourneeCreationRequest request = JourneeCreationRequest.builder()
                .reference("test")
                .date(new Date())
                .tournees(Set.of())
                .build();
        when(journeeMapper.toEntity(any(JourneeCreationRequest.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(BadRequestRestException.class, () -> journeeService.createJournee(request));
    }
}


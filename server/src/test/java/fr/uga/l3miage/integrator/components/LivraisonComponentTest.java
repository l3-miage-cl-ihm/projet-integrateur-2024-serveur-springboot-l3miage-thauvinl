package fr.uga.l3miage.integrator.components;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivraisonComponentTest {

    @Mock
    private LivraisonRepository livraisonRepository;

    @InjectMocks
    private LivraisonComponent livraisonComponent;

    @Test
    public void testGetAllLivraison() {
        // Given
        LivraisonEntity livraison1 = new LivraisonEntity();
        LivraisonEntity livraison2 = new LivraisonEntity();
        List<LivraisonEntity> expectedLivraisons = Arrays.asList(livraison1, livraison2);

        // Mocking the repository behavior
        when(livraisonRepository.findAll()).thenReturn(expectedLivraisons);

        // When
        List<LivraisonEntity> actualLivraisons = livraisonComponent.getAllLivraison();

        // Then
        assertEquals(expectedLivraisons, actualLivraisons);
        verify(livraisonRepository, times(1)).findAll();
    }

    @Test
    public void testGetLivraisonByReference() {
        // Given
        String reference = "REF123";
        LivraisonEntity expectedLivraison = new LivraisonEntity();

        // Mocking the repository behavior
        when(livraisonRepository.findLivraisonEntityByReference(reference)).thenReturn(expectedLivraison);

        // When
        LivraisonEntity actualLivraison = livraisonComponent.getLivraisonByReference(reference);

        // Then
        assertEquals(expectedLivraison, actualLivraison);
        verify(livraisonRepository, times(1)).findLivraisonEntityByReference(reference);
    }

    @Test
    public void testCountElementsInRepo() {
        // Given
        long expectedCount = 5;

        // Mocking the repository behavior
        when(livraisonRepository.count()).thenReturn(expectedCount);

        // When
        long actualCount = livraisonComponent.countElementsInRepo();

        // Then
        assertEquals(expectedCount, actualCount);
        verify(livraisonRepository, times(1)).count();
    }
}

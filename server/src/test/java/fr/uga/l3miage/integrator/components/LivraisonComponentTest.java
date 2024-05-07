package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.components.LivraisonComponent;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class LivraisonComponentTest {

    @MockBean
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LivraisonComponent livraisonComponent;

    @Test
    public void getAllLivraisonOK(){
        LivraisonEntity livraison=LivraisonEntity.builder().build();
        when(livraisonRepository.findAll()).thenReturn(List.of(livraison));
        // when - then
        assertDoesNotThrow(()->livraisonComponent.getAllLivraison());
    }

}

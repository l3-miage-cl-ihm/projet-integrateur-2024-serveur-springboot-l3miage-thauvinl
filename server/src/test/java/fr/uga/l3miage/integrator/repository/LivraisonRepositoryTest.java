package fr.uga.l3miage.integrator.repository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.CommandeRepository;
import fr.uga.l3miage.integrator.repositories.LivraisonRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
@ActiveProfiles("test")
public class LivraisonRepositoryTest {
    @Autowired
    private LivraisonRepository livraisonRepository;
    //@MockBean
    //private CommandeRepository commandeRepository;
    @Test
    public void testFindLivraisonEntityByReferenceFOUND() {

        LivraisonEntity livraisonEntity = new LivraisonEntity();
        livraisonEntity.setReference("REF123");
        /*CommandeEntity cmd= commandeRepository.findCommandeEntityByReference("c001");
        Set<CommandeEntity> cmds=new HashSet<>();
        cmds.add(cmd);
        livraisonEntity.setCommandes(cmds);*/

        livraisonRepository.save(livraisonEntity);


        Optional<LivraisonEntity> result = livraisonRepository.findLivraisonEntityByReference("REF123");

        assertTrue(result.isPresent());
        assertEquals(livraisonEntity.getReference(), result.get().getReference());
    }

    @Test
    public void testFindLivraisonEntityByReferenceNOTFOUND() {

        Optional<LivraisonEntity> result = livraisonRepository.findLivraisonEntityByReference("REF456");


        assertTrue(result.isEmpty());
    }
}

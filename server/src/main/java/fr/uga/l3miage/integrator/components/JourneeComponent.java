package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JourneeComponent {

   private final JourneeRepository journeeRepository;

   private TourneeRepository tourneeRepository;

   public JourneeEntity addTourneeInJournee(String journeeReference, TourneeEntity tourneeEntity) throws NotFoundJourneeEntityException{
       JourneeEntity journeeEntity  = journeeRepository.findByReference(journeeReference).orElseThrow(()->new NotFoundJourneeEntityException(String.format("La journée de référence %s n'a pas été trouvée", journeeReference)));
       journeeEntity.addTournee(tourneeEntity);
       return journeeRepository.save(journeeEntity);
   }
    public JourneeEntity getJourneeByRef(String reference) throws NotFoundJourneeEntityException {
        // Implémentation de la logique pour trouver une Journee par sa référence
        return journeeRepository.findByReference(reference).orElseThrow(()->new NotFoundJourneeEntityException(String.format("La journée [%s] n'a pas été trouvée", reference)));
    }


    public JourneeEntity createJournee(JourneeEntity journee) {
        return journeeRepository.save(journee);
    }

    public void deleteJournee(String reference) throws NotFoundJourneeEntityException{
       JourneeEntity journee = journeeRepository.findByReference(reference).orElseThrow(()->new NotFoundJourneeEntityException(String.format("La journée [%s] n'a pas été trouvée", reference)));
       journeeRepository.delete(journee);
   }
}

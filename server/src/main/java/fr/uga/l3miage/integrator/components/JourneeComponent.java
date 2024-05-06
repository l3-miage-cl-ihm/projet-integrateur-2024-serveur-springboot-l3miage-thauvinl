package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JourneeComponent {

   private final JourneeRepository journeeRepository;
   public JourneeEntity getJourneeByRef(String reference) throws NotFoundJourneeEntityException {
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

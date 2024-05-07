package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import fr.uga.l3miage.integrator.models.TourneeEntity;
import org.springframework.web.server.ResponseStatusException;


@Component
@RequiredArgsConstructor
public class TourneeComponent {
   private final TourneeRepository tourneeRepository;
   private final EmployeRepository employeRepository;


   public TourneeEntity getTourneeByEmploye(String trigramme) throws NotFoundEmployeEntityException, NotFoundTourneeEntityException{
      EmployeEntity employe= employeRepository.findByTrigramme(trigramme).orElseThrow(()->new NotFoundEmployeEntityException(String.format("L'employé d'id %s est introuvable",trigramme)));
      return tourneeRepository.findByEmployeEntitySetContains(employe).orElseThrow(()->new NotFoundTourneeEntityException(String.format("La tournée gérée par l'employé d'id %s n'a pas été trouvée",trigramme)));

   }

   public TourneeEntity updateTdm(String reference, Integer tdmEffectif) throws NotFoundTourneeEntityException{
      TourneeEntity tournee = tourneeRepository.findByReference(reference).orElseThrow(()->new NotFoundTourneeEntityException("Tournée introuvable"));
      try{
         tournee.setTempsDeMontageEffectif(tdmEffectif);
         return tourneeRepository.save(tournee);
      }catch (IllegalArgumentException e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update tournée: " + e.getMessage(), e);
      }
   }
   public void deleteTournee(String ref) throws NotFoundTourneeEntityException{
      TourneeEntity tournee = tourneeRepository.findByReference(ref).orElseThrow(()->new NotFoundTourneeEntityException("Tournée introuvable"));
      tourneeRepository.delete(tournee);
   }
}

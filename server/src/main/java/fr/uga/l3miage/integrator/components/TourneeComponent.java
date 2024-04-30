package fr.uga.l3miage.integrator.components;

import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.repositories.JourneeRepository;
import fr.uga.l3miage.integrator.repositories.TourneeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TourneeComponent {
   private final TourneeRepository tourneeRepository;
   private final EmployeRepository employeRepository;


   public TourneeEntity getTourneeByEmploye(String trigramme) throws NotFoundEmployeEntityException, NotFoundTourneeEntityException{
      EmployeEntity employe= employeRepository.findByTrigramme(trigramme).orElseThrow(()->new NotFoundEmployeEntityException(String.format("L'employé d'id %s est introuvable",trigramme)));
      return tourneeRepository.findByEmployeEntitySetContains(employe).orElseThrow(()->new NotFoundTourneeEntityException(String.format("La tournée gérée par l'employé d'id %s n'a pas été trouvée",trigramme)));

   }

   public void deleteTournee(String ref) throws NotFoundTourneeEntityException{
      TourneeEntity tournee = tourneeRepository.findByReference(ref).orElseThrow(()->new NotFoundTourneeEntityException("Tournée introuvable"));
      tourneeRepository.delete(tournee);
   }
   public TourneeEntity createTournee(TourneeEntity tournee){return tourneeRepository.save(tournee);}

   public TourneeEntity getTourneeByRef(String tourneeReference) throws NotFoundTourneeEntityException {
      return tourneeRepository.findByReference(tourneeReference).orElseThrow(()->new NotFoundTourneeEntityException(String.format("La tournée de référence : %s n'existe pas", tourneeReference)));
   }
   public TourneeEntity addLivraisonInTournee(String reference, LivraisonEntity livraisonEntity) throws NotFoundTourneeEntityException {
      TourneeEntity tourneeEntity  = tourneeRepository.findByReference(reference).orElseThrow(()->new NotFoundTourneeEntityException(String.format("La tournée de référence %s n'a pas été trouvée", reference)));
      Set<LivraisonEntity> livraisons = tourneeEntity.getLivraisons();
      livraisons.add(livraisonEntity);
      tourneeEntity.setLivraisons(livraisons);
      return tourneeRepository.save(tourneeEntity);
   }

}

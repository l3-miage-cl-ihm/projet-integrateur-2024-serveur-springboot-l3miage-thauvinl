package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.*;

import fr.uga.l3miage.integrator.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundCommandeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundJourneeEntityException;
import fr.uga.l3miage.integrator.mappers.JourneeMapper;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.mappers.TourneeMapper;
import fr.uga.l3miage.integrator.models.*;
import fr.uga.l3miage.integrator.models.enums.Emploi;
import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class JourneeService {

    private final JourneeComponent journeeComponent;

    private final JourneeMapper journeeMapper;
    private final TourneeMapper tourneeMapper;
    private final LivraisonMapper livraisonMapper;
    private final EmployeComponent employeComponent;
    private final CamionComponent camionComponent;
    private final CommandeComponent commandeComponent;



    public JourneeResponseDTO getJournee(String reference) {
        try{
            return journeeMapper.toResponseWithTournees(journeeComponent.getJourneeByRef(reference));
        }catch (NotFoundJourneeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }



    public JourneeResponseDTO createJournee(JourneeCreationRequest journeeCreationRequest) {
        try{
            JourneeEntity journeeEntity = journeeMapper.toEntity(journeeCreationRequest);
            for(TourneeCreationRequest tournee : journeeCreationRequest.getTournees()) {
                TourneeEntity tourneeEntity = tourneeMapper.toEntity(tournee);
                journeeEntity.addTournee(tourneeEntity);


                for(LivraisonCreationRequest livraison: tournee.getLivraisons()){
                    LivraisonEntity livraisonEntity = livraisonMapper.toEntity(livraison);


                    for (String cmd : livraison.getRefCommande()) {
                        CommandeEntity commande=commandeComponent.getCommandeByReference(cmd);
                         livraisonEntity.addCommandesInLivraison(commande);

                    }
                    tourneeEntity.addLivraison(livraisonEntity);

                }
                for(String id : tournee.getEmployesIds()){
                    EmployeEntity employe = employeComponent.getEmployeById(id);
                    if(employe.getEmploi()!= Emploi.livreur){
                        throw new IllegalArgumentException(String.format("L'employé %s n'est pas un livreur", employe.getTrigramme()));
                    }
                    if(journeeEntity.getTournees().stream()
                            .anyMatch(tournee1 -> tournee1.getEmployeEntitySet()
                                    .stream()
                                    .anyMatch(employes -> employes.getTrigramme().equals(employe.getTrigramme()))))
                    {
                        throw new IllegalArgumentException(String.format("L'employé %s travaille déjà sur une autre tournée aujourd'hui ", employe.getTrigramme()));
                    }

                    tourneeEntity.getEmployeEntitySet().add(employe);
                }
                String refCamion=tournee.getRefCamion();
                CamionEntity camion=camionComponent.getCamionByRef(refCamion);
                tourneeEntity.setCamion(camion);

            }
            return journeeMapper.toResponseWithTournees(journeeComponent.createJournee(journeeMapper.toEntity(journeeCreationRequest)));
        }catch (IllegalArgumentException e){
            throw new BadRequestRestException(e.getMessage());
        } catch (NotFoundCommandeEntityException e) {
            throw new RuntimeException(e);
        } catch (NotFoundEmployeEntityException e) {
            throw new RuntimeException(e);
        }
    }

}

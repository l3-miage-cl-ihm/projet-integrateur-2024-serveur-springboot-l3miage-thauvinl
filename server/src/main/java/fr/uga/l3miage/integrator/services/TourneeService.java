package fr.uga.l3miage.integrator.services;

import fr.uga.l3miage.integrator.components.TourneeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundLivraisonEntityException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundTourneeEntityException;
import fr.uga.l3miage.integrator.mappers.LivraisonMapper;
import fr.uga.l3miage.integrator.mappers.TourneeMapper;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TourneeService {
    private final TourneeMapper tourneeMapper;
    private final TourneeComponent tourneeComponent;
    public TourneeResponseDTO getTourneeByEmploye(String trigramme) {
        try {
            return tourneeMapper.toResponse(tourneeComponent.getTourneeByEmploye(trigramme));
        } catch (NotFoundTourneeEntityException | NotFoundEmployeEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());

        }
    }

    public TourneeResponseDTO updateTdmEffectifTournee(String reference, Integer tdmEffectif){
        try {
            return tourneeMapper.toResponse(tourneeComponent.updateTdm(reference, tdmEffectif));
        }catch ( NotFoundTourneeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public TourneeResponseDTO updateEtat(String reference, String nvEtat){
        try{
            return tourneeMapper.toResponse(tourneeComponent.updateEtat(reference, nvEtat));
        }catch (NotFoundTourneeEntityException e){
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

}

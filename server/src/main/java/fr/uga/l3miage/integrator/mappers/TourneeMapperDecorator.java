package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.components.CamionComponent;
import fr.uga.l3miage.integrator.components.EmployeComponent;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.integrator.exceptions.technical.NotFoundEmployeEntityException;
import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.models.enums.EtatDeTournee;
import fr.uga.l3miage.integrator.repositories.EmployeRepository;
import fr.uga.l3miage.integrator.requests.TourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class TourneeMapperDecorator implements TourneeMapper {

    @Autowired
    @Qualifier("delegate")
    private TourneeMapper delegate;

    @Autowired
    private LivraisonMapper livraisonMapper;

    @Autowired CamionMapper camionMapper;

    @Autowired
    private EmployeComponent employeComponent;

    @Autowired
    private CamionComponent camionComponent;
    @Override
    public TourneeEntity toEntity(TourneeCreationRequest request){
        TourneeEntity tournee= delegate.toEntity(request);
        tournee.setEtatsDeTournee(EtatDeTournee.planifiee);
        tournee.setLivraisons(new HashSet<>());
        tournee.setEmployeEntitySet(new HashSet<>());
        request.getEmployesIds().forEach(id -> {
            try {
                EmployeEntity employe = employeComponent.getEmployeById(id);
                tournee.addEmploye(employe);
            }catch (NotFoundEmployeEntityException e){
                throw new NotFoundEntityRestException(e.getMessage());
            }
        });
        request.getLivraisons().stream().map(livraisonMapper::toEntity).forEach(tournee::addLivraison);
        tournee.setCamion(camionComponent.getCamionByRef(request.getRefCamion()));
        return tournee;
    }

    @Override
    public TourneeResponseDTO toResponse(TourneeEntity tournee){
        TourneeResponseDTO responseDTO = delegate.toResponse(tournee);
        responseDTO.setCamionResponseDTO(camionMapper.toResponse(tournee.getCamion()));

        if (tournee.getLivraisons() == null || tournee.getLivraisons().isEmpty()) {
            responseDTO.setTempsDeMontageTheorique(0);
            responseDTO.setMontant(0.0);
            responseDTO.setDistanceAParcourir(0.0);
        }
        else {
            Set<LivraisonResponseDTO> livraisonResponses = tournee.getLivraisons().stream()
                    .map(livraisonMapper::toResponse)
                    .collect(Collectors.toSet());
            responseDTO.setLivraisonResponseDTOS(livraisonResponses);
            responseDTO.setTempsDeMontageTheorique(responseDTO.getLivraisonResponseDTOS().stream()
                    .mapToInt(LivraisonResponseDTO::getTdmTheorique)
                    .sum());
            responseDTO.setMontant(responseDTO.getLivraisonResponseDTOS().stream()
                    .mapToDouble(LivraisonResponseDTO::getMontant)
                    .sum());
            responseDTO.setDistanceAParcourir(responseDTO.getLivraisonResponseDTOS().stream()
                    .mapToDouble(LivraisonResponseDTO::getDistanceAParcourir)
                    .sum());
        }
        return responseDTO;
    }
}

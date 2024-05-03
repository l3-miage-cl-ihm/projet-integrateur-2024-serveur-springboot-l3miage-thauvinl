package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LivraisonEntity;
import fr.uga.l3miage.integrator.requests.LivraisonCreationRequest;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LivraisonMapperDecorator implements LivraisonMapper{
    @Autowired
    @Qualifier("delegate")
    private LivraisonMapper livraisonMapper;

    @Autowired
    private CommandeMapper commandeMapper;

    @Override
    public LivraisonEntity toEntity(LivraisonCreationRequest request) {
        LivraisonEntity livraisonEntity = livraisonMapper.toEntity(request);
        //livraisonEntity.setCommandes(Set.of());
        Set<CommandeEntity> cmd=new HashSet<>();
        livraisonEntity.setCommandes(cmd);
        return livraisonEntity;
    }

    @Override
    public LivraisonResponseDTO toResponse(LivraisonEntity livraisonEntity) {

        LivraisonResponseDTO responseDTO =  livraisonMapper.toResponse(livraisonEntity);
        System.out.println(livraisonEntity.getCommandes().size());

        // Vérifiez si livraisonEntity.getCommandes() est null ou vide
        if (livraisonEntity.getCommandes() == null || livraisonEntity.getCommandes().isEmpty()) {
            // Si c'est le cas, affectez les valeurs par défaut
            responseDTO.setMontant(10);
            responseDTO.setTdmTheorique(15);
            responseDTO.setDistanceParcourue(100);
        } else {
            // Si livraisonEntity.getCommandes() n'est pas vide, mappez les commandes en CommandeResponseDTO
            Set<CommandeResponseDTO> commandeResponseDTOS = livraisonEntity.getCommandes().stream()
                    .map(commandeMapper::toResponse) // Utilisation de commandemapper pour convertir
                    .collect(Collectors.toSet()); // Collecter les résultats dans un Set

            // Assurez-vous que vous utilisez les valeurs correctes pour setMontant, setTdmTheorique, etc.
            // J'ai utilisé des valeurs arbitraires ici. Vous devriez utiliser les valeurs appropriées.
            responseDTO.setMontant(10);
            responseDTO.setTdmTheorique(15);
            responseDTO.setDistanceParcourue(100);
            // Assurez-vous d'ajouter les commandes mappées à la réponse
            responseDTO.setCommandes(commandeResponseDTOS);
        }
        return responseDTO;
    }

}

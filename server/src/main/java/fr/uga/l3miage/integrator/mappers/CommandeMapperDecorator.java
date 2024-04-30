package fr.uga.l3miage.integrator.mappers;


import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CommandeMapperDecorator implements CommandeMapper{
    @Autowired
    private CommandeMapper commandeMapper;

   // @Autowired
    //private LivraisonMapper livraisonMapper;


    public CommandeResponseDTO toCommandeResponseDTO(CommandeEntity entity){
        CommandeResponseDTO commandeResponseDTO=commandeMapper.toCommandeResponseDTO(entity);
        if (entity.getLivraison() == null) {
            commandeResponseDTO.setMontant(0.0F);
            commandeResponseDTO.setTdmTheorique(0);
        } else {

            //commandeResponseDTO.setLivraison(livraisonMapper.toLivraisonResponseDTO(entity.getLivraison()));
            //ici a rajoutter autre calcul par rapport a livraison
        }
        return commandeResponseDTO;
    }
    public Set<CommandeResponseDTO> toCommandeResponseDTOList(Set<CommandeEntity> entities){
        return entities.stream()
                .map(this::toCommandeResponseDTO)
                .collect(Collectors.toSet());
    }
}

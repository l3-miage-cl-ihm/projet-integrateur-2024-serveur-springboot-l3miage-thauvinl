package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.CommandeEntity;
import fr.uga.l3miage.integrator.models.LigneEntity;
import fr.uga.l3miage.integrator.models.ProduitEntity;
import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;
import fr.uga.l3miage.integrator.responses.LivraisonResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class CommandeMapperDecorator implements CommandeMapper{
    @Autowired
    @Qualifier("delegate")
    private CommandeMapper delegate;
    @Override
    public CommandeResponseDTO toResponseDTO(CommandeEntity commande){
        CommandeResponseDTO responseDTO = delegate.toResponseDTO(commande);
        if (commande.getLignesCommandes() == null || commande.getLignesCommandes().isEmpty()) {
            responseDTO.setTdmTheorique(0);
            responseDTO.setMontant(0.0);
        }
        else {

            responseDTO.setTdmTheorique(commande.getLignesCommandes().stream()
                    .filter(ligne -> ligne.getProduit().getOptionDeMontage())
                    .mapToInt(ligne -> ligne.getProduit().getTempsDeMontageTheorique() * ligne.getQuantite())
                    .sum());
            responseDTO.setMontant(commande.getLignesCommandes().stream()
                    .mapToDouble(LigneEntity::getMontant)
                    .sum());
        }

    return responseDTO;
        }
}

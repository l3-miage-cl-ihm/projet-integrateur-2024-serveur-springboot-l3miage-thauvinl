package fr.uga.l3miage.integrator.requests;

import fr.uga.l3miage.integrator.responses.CommandeResponseDTO;

import java.sql.Time;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LivraisonCreationRequest {


        private final String etat;

        private float montant;

        private float distanceParcourue;

        private Integer tdtALAller;

        private Integer tdpTheorique;

        private Integer tddTheorique;

        private Integer tdmTheorique;

        private Time heureDeLivraisonTheorique;

        private Time heureDeLivraisonEffective;

        private Integer tdmEffectif;

        private final Set<CommandeResponseDTO> commandes;

    }


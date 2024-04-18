package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.enums.EtatDeClient;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    private String email;
    private String prenom;
    private String nom;
    private EtatDeClient etat;
    private Float montantTotal;
    @Embedded
    private Adresse adresse;

    @OneToMany()
    private Set<CommandeEntity> commandes;


}

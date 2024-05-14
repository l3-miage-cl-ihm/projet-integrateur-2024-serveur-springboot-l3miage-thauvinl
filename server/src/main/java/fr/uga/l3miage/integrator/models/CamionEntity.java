package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.dataType.GeoPosition;
import lombok.*;


import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CamionEntity {
    @Id
    private String immatriculation;

    @Embedded
    private GeoPosition position;

}

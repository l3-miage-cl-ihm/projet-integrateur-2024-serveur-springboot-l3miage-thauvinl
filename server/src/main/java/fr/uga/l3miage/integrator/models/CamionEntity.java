package fr.uga.l3miage.integrator.models;

import fr.uga.l3miage.integrator.dataType.GeoPosition;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter
@Setter
public class CamionEntity {
    @Id
    private String immatriculation;

    @Embedded
    private GeoPosition position;

}

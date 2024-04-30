package fr.uga.l3miage.integrator.dataType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoPosition {
    private Double latitude;
    private Double longitude;

}

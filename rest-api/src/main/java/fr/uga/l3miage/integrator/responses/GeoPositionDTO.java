package fr.uga.l3miage.integrator.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoPositionDTO {
    private Double latitude;
    private Double longitude;
}

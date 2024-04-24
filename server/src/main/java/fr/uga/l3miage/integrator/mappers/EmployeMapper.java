package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.requests.EmployeCreationRequest;
import fr.uga.l3miage.integrator.responses.EmployeResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeMapper {
    EmployeResponseDTO toResponse (EmployeEntity employe);
    EmployeEntity toEntity(EmployeCreationRequest request);


}

package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.models.CamionEntity;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import fr.uga.l3miage.integrator.responses.CamionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdresseMapper{
    //@Mapping(source = "immatriculation", target = "immatriculation")
    AdresseResponseDTO toResponse(Adresse a);}
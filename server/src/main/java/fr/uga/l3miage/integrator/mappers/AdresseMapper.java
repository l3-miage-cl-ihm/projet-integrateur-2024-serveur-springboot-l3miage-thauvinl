package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.dataType.Adresse;
import fr.uga.l3miage.integrator.responses.AdresseResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AdresseMapper{
    AdresseResponseDTO toResponse(Adresse a);}
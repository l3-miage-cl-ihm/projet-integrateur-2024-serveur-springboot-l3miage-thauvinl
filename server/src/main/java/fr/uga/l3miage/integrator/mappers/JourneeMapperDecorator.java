package fr.uga.l3miage.integrator.mappers;

import fr.uga.l3miage.integrator.models.JourneeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import fr.uga.l3miage.integrator.requests.JourneeCreationRequest;
import fr.uga.l3miage.integrator.responses.JourneeResponseDTO;
import fr.uga.l3miage.integrator.responses.TourneeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JourneeMapperDecorator implements  JourneeMapper{

    @Autowired
    @Qualifier("delegate")
    private JourneeMapper delegate;

    @Autowired
    private TourneeMapper tourneeMapper;



    @Override
    public JourneeEntity toEntity(JourneeCreationRequest request){
        JourneeEntity journee = delegate.toEntity(request);
        journee.setTournees(new HashSet<>());
        request.getTournees().stream().map(tourneeMapper::toEntity).forEach(journee::addTournee);
        return journee;
    }

}

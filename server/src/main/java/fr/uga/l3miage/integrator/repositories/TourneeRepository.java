package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.EmployeEntity;
import fr.uga.l3miage.integrator.models.TourneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourneeRepository extends JpaRepository<TourneeEntity, String> {
    Optional<TourneeEntity> findByReference(String reference);
    Optional<TourneeEntity> findByEmployeEntitySetContains(EmployeEntity employe);
}
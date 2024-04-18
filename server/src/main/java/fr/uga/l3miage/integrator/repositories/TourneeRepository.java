package fr.uga.l3miage.integrator.repositories;

import fr.uga.l3miage.integrator.models.TourneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourneeRepository extends JpaRepository<TourneeEntity, String> {
    List<TourneeEntity> findByReferenceContaining(String reference);
}
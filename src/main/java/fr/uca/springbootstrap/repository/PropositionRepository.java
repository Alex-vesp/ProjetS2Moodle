package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Proposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {
}

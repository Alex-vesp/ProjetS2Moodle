package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findByName(String name);

    Optional<Cours> findById(String name);
}

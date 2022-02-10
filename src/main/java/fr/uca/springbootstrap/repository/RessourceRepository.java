package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
@NoRepositoryBean
public interface RessourceRepository extends JpaRepository<Ressource, Long> {
}

package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Responses;
import fr.uca.springbootstrap.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsesRepository extends JpaRepository<Responses, Long> {

}

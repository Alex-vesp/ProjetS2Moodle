package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionOuverteRepository  extends JpaRepository<QuestionOuverte, Long>{
    Optional<QuestionOuverte> findByText(String name);

    Optional<QuestionOuverte> findById(String name);
    
}

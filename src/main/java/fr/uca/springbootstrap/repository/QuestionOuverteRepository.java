package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.QuestionOuverte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOuverteRepository  extends JpaRepository<QuestionOuverte, Long>{
    
}

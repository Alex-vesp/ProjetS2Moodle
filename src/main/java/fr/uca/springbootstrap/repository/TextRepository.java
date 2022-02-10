package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {
}

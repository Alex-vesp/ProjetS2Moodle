package fr.uca.springbootstrap.repository;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.QCM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QcmRepository extends JpaRepository<QCM, Long> {
        Optional<QCM> findById(String name);
        Optional<QCM> findByText(String name);



}

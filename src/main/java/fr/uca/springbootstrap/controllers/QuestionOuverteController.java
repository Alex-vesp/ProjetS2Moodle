package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.QuestionOuverteRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questionsOuvertes")
public class QuestionOuverteController {
    @Autowired
    QuestionnaireRepository questionnaireRepository;
    @Autowired
    QuestionOuverteRepository questionOuverteRepository;


    @DeleteMapping("/{questouvID}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteCours(@PathVariable long questouvID) {
        Optional<QuestionOuverte> oquestO = questionOuverteRepository.findById(questouvID);
        if (!oquestO.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Question ouverte!"));
        }
        questionOuverteRepository.delete(oquestO.get());
        return ResponseEntity.ok(new MessageResponse("Question ouverte deleted !"));
    }


    @GetMapping("")
    public ResponseEntity<?> getAllTexts() {

        return ResponseEntity.ok(questionOuverteRepository.findAll().toString());
    }

    @GetMapping("/{questouvID}")
    public ResponseEntity<?> getCours(@PathVariable long questouvID) {
        Optional<QuestionOuverte> oquesto = questionOuverteRepository.findById(questouvID);
        if (!oquesto.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Question !"));
        }
        return ResponseEntity.ok(oquesto.get().toString());
    }
}

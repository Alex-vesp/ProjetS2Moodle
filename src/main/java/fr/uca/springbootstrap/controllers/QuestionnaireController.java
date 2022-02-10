package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.payload.request.addQuestionOuverteRequest;
import fr.uca.springbootstrap.payload.request.addTextRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.QuestionOuverteRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireRepository questionnaireRepository;
    @Autowired
    QuestionOuverteRepository questionOuverteRepository;



    @DeleteMapping("/{questID}")
    public ResponseEntity<?> deleteCours(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        questionnaireRepository.delete(oquest.get());
        return ResponseEntity.ok(new MessageResponse("questionnaire deleted !"));


    }

    @GetMapping("/{questID}")
    public ResponseEntity<?> getCours(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        return ResponseEntity.ok(oquest.get().toString());
    }
    @GetMapping("")
    public ResponseEntity<?> getAllCours() {

        return ResponseEntity.ok(questionnaireRepository.findAll().toString());
    }
    @GetMapping("/{questID}/questionsOuvertes")
    public ResponseEntity<?> gettexts(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }

        return ResponseEntity.ok(oquest.get().getQsts().toString());
    }

    @PostMapping("/{questID}/questionsOuvertes")
    public ResponseEntity<?> settexts(@Valid @RequestBody addQuestionOuverteRequest addQuestionOuverteRequest, @PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        QuestionOuverte questionOuverte= new QuestionOuverte(addQuestionOuverteRequest.getText(),addQuestionOuverteRequest.getReponse());
       System.out.println(questionOuverte);
        questionOuverteRepository.save(questionOuverte);
        Questionnaire questionnaire = oquest.get();
        questionnaire.getQsts().add(questionOuverte);
       questionnaireRepository.save(questionnaire);
        return ResponseEntity.ok(new MessageResponse("Question ouverte registered successfully!"));
    }

}

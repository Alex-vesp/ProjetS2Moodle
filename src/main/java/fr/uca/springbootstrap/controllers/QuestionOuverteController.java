package fr.uca.springbootstrap.controllers;


import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.payload.request.addQuestionOuverteRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.QuestionOuverteRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.TextRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",oquestO.get().getId());
        jsonObject.toString();
        questionOuverteRepository.delete(oquestO.get());
        return ResponseEntity.ok(jsonObject.toString());
    }

    @PutMapping("/{questouvID}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> modifieQuestionnaireOuvert(@Valid @RequestBody addQuestionOuverteRequest addQuestionOuverteRequest, @PathVariable long questouvID) {
        Optional<QuestionOuverte> oquestO = questionOuverteRepository.findById(questouvID);
        if (!oquestO.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Question ouverte!"));
        }
        QuestionOuverte questionOuverte = oquestO.get();
        questionOuverte.setText(addQuestionOuverteRequest.getText());
        questionOuverte.setReponse(addQuestionOuverteRequest.getReponse());
        questionOuverteRepository.save(questionOuverte);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",oquestO.get().getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
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

package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddRessourceRequest;
import fr.uca.springbootstrap.payload.request.LoginRequest;
import fr.uca.springbootstrap.payload.request.addTextRequest;
import fr.uca.springbootstrap.payload.response.JwtResponse;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.RessourceRepository;
import fr.uca.springbootstrap.repository.TextRepository;
import fr.uca.springbootstrap.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ressources")
public class RessourceController {

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;
    @Autowired
    TextRepository textRepository;

    @PostMapping("/cours")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AddRessourceRequest addRessourceRequest) {
        Cours cours= new Cours(addRessourceRequest.getName(),addRessourceRequest.getDes());
        coursRepository.save(cours);
        return ResponseEntity.ok(new MessageResponse("cours registered successfully!"));
    }

    @PostMapping("/questionnaire")
    public ResponseEntity<?> addQuestionnaire(@Valid @RequestBody AddRessourceRequest addRessourceRequest) {
        Questionnaire questionnaire= new Questionnaire(addRessourceRequest.getName(),addRessourceRequest.getDes());
        questionnaireRepository.save(questionnaire);
        return ResponseEntity.ok(new MessageResponse("questionnaire registered successfully!"));
    }

    @PostMapping("/cours/{coursID}")
    public ResponseEntity<?> addText(@Valid @RequestBody addTextRequest addTextRequest, @PathVariable long coursID) {

        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        System.out.println(addTextRequest.getText());
        Text text= new Text(addTextRequest.getText());
        textRepository.save(text);
        Cours cours = ocours.get();
        cours.getTexts().add(text);
        coursRepository.save(cours);
        return ResponseEntity.ok(new MessageResponse("Text registered successfully!"));
    }
    @DeleteMapping("/cours/{coursID}/{TextId}")
    public ResponseEntity<?> addText(@PathVariable long TextId,@PathVariable long coursID) {

        Optional<Text> otext = textRepository.findById(TextId);
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        if (!otext.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such text!"));
        }
        Cours cours=ocours.get();
        cours.getTexts().remove(otext.get());
        coursRepository.save(cours);
        textRepository.delete(otext.get());
        return ResponseEntity.ok(new MessageResponse("Text id"+TextId+"deleted"));
    }



}

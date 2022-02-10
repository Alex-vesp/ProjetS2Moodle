package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.QST;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.Ressource;
import fr.uca.springbootstrap.payload.request.AddQstRequest;
import fr.uca.springbootstrap.payload.request.AddRessourceRequest;
import fr.uca.springbootstrap.payload.request.LoginRequest;
import fr.uca.springbootstrap.payload.response.JwtResponse;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.QSTRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.RessourceRepository;
import fr.uca.springbootstrap.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    QSTRepository qstRepository;

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
    @PostMapping("/questionnaire/{questionnaireID}")
    public ResponseEntity<?> addQst(@Valid @RequestBody AddQstRequest addQstRequest) {
        System.out.println(addQstRequest.getQstType());
        QST qst= new QST(addQstRequest.getQstText(),Integer.parseInt(addQstRequest.getQstType()));
        qstRepository.save(qst);
        return ResponseEntity.ok(new MessageResponse("qst registered successfully!"));
    }

}

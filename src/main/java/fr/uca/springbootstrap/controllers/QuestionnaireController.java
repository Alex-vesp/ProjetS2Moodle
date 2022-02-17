package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddRessourceRequest;
import fr.uca.springbootstrap.payload.request.addQuestionOuverteRequest;
import fr.uca.springbootstrap.payload.request.addTextRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    PropositionRepository propositionRepository;
    @Autowired
    QcmRepository qcmRepository;




    @DeleteMapping("/{questID}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteQuestionnaire(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }


        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",oquest.get().getId());
        jsonObject.toString();
        questionnaireRepository.delete(oquest.get());
        return ResponseEntity.ok(jsonObject.toString());


    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{questionnaireID}")
    public ResponseEntity<?> modifieQuestionnaire(@Valid @RequestBody AddRessourceRequest addRessourceRequest,  @PathVariable long questionnaireID) {
        Optional<Questionnaire> oquestionnaire = questionnaireRepository.findById(questionnaireID);
        if (!oquestionnaire.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        //recuperation du cours et le modifier
        Questionnaire questionnaire = oquestionnaire.get();
        questionnaire.setName(addRessourceRequest.getName());
        questionnaire.setDes(addRessourceRequest.getDes());
        questionnaireRepository.save(questionnaire);
        //recuperation du module
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",oquestionnaire.get().getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping("/{questID}")
    public ResponseEntity<?> getCours(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",oquest.get().getId());
        jsonObject.put("name",oquest.get().getName());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
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

    //get QCM of questionnaire
    @GetMapping("/{questID}/Qcm")
    public ResponseEntity<?> getqQcms(@PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }

        return ResponseEntity.ok(oquest.get().getQcms().toString());
    }
    //get QCM of questionnaire
    @GetMapping("/{questID}/Qcm/{QcmID}")
    public ResponseEntity<?> getqQcms(@PathVariable long questID,@PathVariable long QcmID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        Optional<QCM> oqcm = qcmRepository.findById(QcmID);
        if (!oqcm.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such QCM!"));
        }

        return ResponseEntity.ok(oqcm.get().toString());
    }

    //get Propositons  of Qcm
    @GetMapping("/{questID}/Qcm/{QcmID}/propositions")
    public ResponseEntity<?> getprops(@PathVariable long questID,@PathVariable long QcmID) {
        Optional<QCM> oqcm = qcmRepository.findById(QcmID);
        if (!oqcm.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such QCM!"));
        }

        return ResponseEntity.ok(oqcm.get().getPropositions().toString());
    }


    @PostMapping("/{questID}/questionsOuvertes")
    @PreAuthorize("hasRole('TEACHER')")
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
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",questionOuverte.getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }


   //post new Qcm
    @PostMapping("/{questID}/Qcm")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addQcm(@Valid @RequestBody addQuestionOuverteRequest addQuestionOuverteRequest, @PathVariable long questID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        QCM qcm= new QCM(addQuestionOuverteRequest.getText(),addQuestionOuverteRequest.getReponse());
        qcmRepository.save(qcm);
        Questionnaire questionnaire = oquest.get();
        questionnaire.getQcms().add(qcm);
        questionnaireRepository.save(questionnaire);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",qcm.getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }

    //post new prop
    @PostMapping("/{questID}/Qcm/{QcmID}/propositions")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addprops(@Valid @RequestBody addTextRequest addTextRequest, @PathVariable long questID, @PathVariable long qcmID) {
        Optional<Questionnaire> oquest = questionnaireRepository.findById(questID);
        if (!oquest.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such questionnaire!"));
        }
        Optional<QCM> oqcm = qcmRepository.findById(qcmID);
        if (!oqcm.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such QCM!"));
        }

        Proposition proposition= new Proposition(addTextRequest.getText());
        propositionRepository.save(proposition);
        oqcm.get().getPropositions().add(proposition);
        qcmRepository.save(oqcm.get());
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",proposition.getId());
        return ResponseEntity.ok(jsonObject.toString());
    }

}

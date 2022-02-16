package fr.uca.springbootstrap.controllers;


import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.payload.request.AddRessourceRequest;
import fr.uca.springbootstrap.payload.request.addTextRequest;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
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
@RequestMapping("/api/cours")
public class CoursController {
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    TextRepository textRepository;

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{coursID}")
    public ResponseEntity<?> deleteCours(@PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",ocours.get().getId());
        jsonObject.toString();
        coursRepository.delete(ocours.get());
        return ResponseEntity.ok(jsonObject.toString());



    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{coursID}")
    public ResponseEntity<?> modifieCours(@Valid @RequestBody AddRessourceRequest addRessourceRequest,@PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        Cours cours = ocours.get();
        cours.setName(addRessourceRequest.getName());
        cours.setDes(addRessourceRequest.getDes());
        coursRepository.save(cours);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",ocours.get().getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping("/{coursID}")
    public ResponseEntity<?> getCours(@PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",ocours.get().getId());
        jsonObject.put("name",ocours.get().getName());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }
    @GetMapping("")
    public ResponseEntity<?> getAllCours() {

        return ResponseEntity.ok(coursRepository.findAll().toString());
    }

    @GetMapping("/{coursID}/texts")
    public ResponseEntity<?> gettexts(@PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }

        return ResponseEntity.ok(ocours.get().getTexts().toString());
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{coursID}/texts")
    public ResponseEntity<?> settexts(@Valid @RequestBody addTextRequest addTextRequest, @PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        if (!ocours.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such cours!"));
        }
        Text text= new Text(addTextRequest.getText());
        textRepository.save(text);
        Cours cours = ocours.get();
        cours.getTexts().add(text);
        coursRepository.save(cours);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",text.getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{coursID}/texts/{textID}")
    public ResponseEntity<?> modifieTexts(@Valid @RequestBody addTextRequest addTextRequest, @PathVariable long textID,@PathVariable long coursID) {
        Optional<Cours> ocours = coursRepository.findById(coursID);
        Optional<Text> otexts = textRepository.findById(textID);
        if (!otexts.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Text!"));
        }
        Text text = otexts.get();
        Cours cours = ocours.get();
        cours.getTexts().remove(text);
        text.setText(addTextRequest.getText());
        textRepository.save(text);
        cours.getTexts().add(text);
        coursRepository.save(cours);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",otexts.get().getId());
        jsonObject.toString();
        return ResponseEntity.ok(jsonObject.toString());
    }

}

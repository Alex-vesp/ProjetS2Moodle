package fr.uca.springbootstrap.controllers;

import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.payload.response.MessageResponse;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.TextRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/texts")
public class TextController {
    @Autowired
    CoursRepository coursRepository;
    @Autowired
    TextRepository textRepository;


    @DeleteMapping("/{textID}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteCours(@PathVariable long textID) {
        Optional<Text> otexts = textRepository.findById(textID);
        if (!otexts.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Text!"));
        }
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",otexts.get().getId());
        jsonObject.toString();
        textRepository.delete(otexts.get());
        return ResponseEntity.ok(jsonObject.toString());
    }


    @GetMapping("")
    public ResponseEntity<?> getAllTexts() {

        return ResponseEntity.ok(textRepository.findAll().toString());
    }

    @GetMapping("/{textID}")
    public ResponseEntity<?> getCours(@PathVariable long textID) {
        Optional<Text> otext = textRepository.findById(textID);
        if (!otext.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: No such Text!"));
        }
        return ResponseEntity.ok(otext.get().toString());
    }

}

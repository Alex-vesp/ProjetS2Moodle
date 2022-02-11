package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddModuleRequest;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetCours  extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    CoursRepository coursRepository;



    @Given("a cours with name {string} and id {long} and description {string}")
    public void aCoursWithNameAndIdAndDescription(String arg0, Long id, String arg1) {
        Cours cours = coursRepository.findByName(arg0)
                .orElse(new Cours(arg0, arg1));
        coursRepository.save(cours);
    }

    @When("{string} get the cours of name {string} and id {long}")
    public void getTheCoursOfName(String arg0, String arg1, Long ids) throws IOException {
        Optional<Cours> cours = coursRepository.findById(ids);
        Cours response = new Cours();
        if (cours.isPresent()){
            response = cours.get();
        }
        int id = cours.get().getId().intValue();
        executeGet("http://localhost:8080/api/cours/" + Integer.toString(id));
    }

    @Then("he can visualize the name {string} and the description {string}")
    public void heCanVisualizeTheNameAndTheDescription(String arg0, String arg1) {
        Optional<Cours> ocours = coursRepository.findByName(arg0);
        assertTrue(ocours.isPresent());
    }
}

package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteQuestionnaireStepDefs extends SpringIntegration{

    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;

    @And("And a module named {string}")
    public void andAMOduleNamed(String arg0) {
        Module module = moduleRepository.findByName(arg0).orElse(new Module(arg0));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }

    @And("a questionnaire named {string}")
    public void aQuestionnaireNamed(String arg0) {
        Questionnaire questionnaire = questionnaireRepository.findByName(arg0).orElse(new Questionnaire(arg0,"pst"));
        questionnaireRepository.save(questionnaire);
    }

    @When("{string} delete questionnaire named  {string} in {string}")
    public void deleteQuestionnaireNamedIn(String arg0, String arg1, String arg2) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        //supprimer si le module avec ce nom existe déja :
        Optional<Questionnaire> oquestionnaire= questionnaireRepository.findByName(arg1);
        executeDelete("http://localhost:8080/api/questionnaire/"+oquestionnaire.get().getId(),jwt);

    }

    @Then("{string} is  not registered to questionnaire in {string}")
    public void isNotRegisteredToQuestionnaireIn(String arg0, String arg1) {
        Optional<Questionnaire> oquestionnaire = questionnaireRepository.findByName(arg0);
        //TODO
        assertTrue(oquestionnaire.isPresent());

    }
}

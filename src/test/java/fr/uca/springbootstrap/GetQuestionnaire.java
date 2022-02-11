package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddModuleRequest;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.QuestionnaireRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
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

public class GetQuestionnaire  extends SpringIntegration{
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

    @And("a questionnaire with name {string} and id {long} and description {string}")
    public void aQuestionnaireWithName(String arg0, Long arg1, String arg2) {
        Questionnaire questionnaire = questionnaireRepository.findById(arg1)
                .orElse(new Questionnaire(arg0, arg2));
        questionnaireRepository.save(questionnaire);
    }

    @When("{string} get the questionnaire of id {long}")
    public void getTheQuestionnaireOfId(String arg0, Long arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(arg1);
        Questionnaire response = new Questionnaire();
        if (questionnaire.isPresent()){
            response = questionnaire.get();
        }
        executeGet("http://localhost:8080/api/questionnaire/"+arg0);
    }

    @Then("he can visualize the name {string}, the id {long} and the description {string}")
    public void heCanVisualizeTheNameTheIdAndTheDescription(String arg0, long arg1, String arg2) {
        Optional<Questionnaire> oquestionnaire = questionnaireRepository.findById(arg1);
        assertTrue(oquestionnaire.isPresent());
    }




}

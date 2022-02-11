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

public class GetQuestionOuverte  extends SpringIntegration{
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
    QuestionOuverteRepository questionOuverteRepository;


    @Given("a question ouverte with a text {string} and a reponse {string} and an id of {long}")
    public void aQuestionOuverteWithATextAndAReponseAndAnIdOf(String arg0, String arg1, long arg2) {
        QuestionOuverte questionOuverte = questionOuverteRepository.findById(arg2)
                .orElse(new QuestionOuverte(arg0, arg1));
        questionOuverteRepository.save(questionOuverte);
    }

    @When("{string} get the question ouverte of id {long}")
    public void getTheQuestionOuverteOfId(String arg0, long arg1) throws IOException {
        Optional<QuestionOuverte> questionOuverteOptional = questionOuverteRepository.findById(arg1);
        QuestionOuverte response = new QuestionOuverte();
        if (questionOuverteOptional.isPresent()){
            response = questionOuverteOptional.get();
        }
        executeGet("http://localhost:8080/api/questionsOuvertes/"+ arg1);
    }

    @Then("he can visualize the text {string} of the question of id {long}")
    public void heCanVisualizeTheTextOfTheQuestion(String arg0, long arg1) {
        Optional<QuestionOuverte> optionalQuestionOuverte = questionOuverteRepository.findById(arg1);
        assertTrue(optionalQuestionOuverte.isPresent());
    }
}

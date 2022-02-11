package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterQuestionnaireStepDef extends SpringIntegration{
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

    @When("{string} registers  questionnaire named  {string} in {string}")
    public void registersCoursNamedIn(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        Module module= moduleRepository.findByName(arg2).get();

        String obj="{\"name\":\""+arg1+"\"," +
               "\"des\":\""+"content"+"\""+
                "}";


        Optional <Questionnaire> oquestionnaire = questionnaireRepository.findByName(arg1);
        if(!oquestionnaire.isPresent()) {
        executeOPost("http://localhost:8080/api/module/"+module.getId()+"/Ressources/questionnaire",jwt,obj);}


    }

    @Then("{string} is registered to questionnaire in {string}")
    public void isRegisteredToQuestionnaire(String arg0, String arg1) {
        Optional<Questionnaire> oquestionnaire = questionnaireRepository.findByName(arg0);
        assertTrue(oquestionnaire.isPresent());
    }
}

package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PutQuestionnaireStepDef extends SpringIntegration{
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
    long id;
    @When("{string} put questionnaire named  {string} to {string}")
    public void putQuestionnaireNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Questionnaire> oquest= questionnaireRepository.findByName(arg1);
        id=oquest.get().getId();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",arg2);
        jsonObject.put("des","changer1");
        System.out.println(jsonObject.toString());
        executePut("http://localhost:8080/api/questionnaire/"+id,jwt,jsonObject.toString());
        Optional<Questionnaire> oquest1= questionnaireRepository.findById(id);
        System.out.println(oquest1.get().getName());

    }

    @Then("{string}   is named    {string}")
    public void isNamed(String arg0, String arg1) {
        Optional<Questionnaire> oquest= questionnaireRepository.findById(id);
        assertTrue(oquest.get().getName().equals(arg1));
    }






    @Then("{string} is not   changed to {string}")
    public void isNotChangedTo(String arg0, String arg1) {
        Optional<Questionnaire> oquest= questionnaireRepository.findById(id);
        assertTrue(!oquest.get().getName().equals(arg1));
    }



    @And("Then last  delete  request  statusgs  is  {int}")
    public void thenLastPutRequestStatusgsIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

    @And("Then last  put  request  status  is {int}")
    public void thenLastPutRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

}

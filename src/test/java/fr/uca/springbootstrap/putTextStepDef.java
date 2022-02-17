package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Questionnaire;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class putTextStepDef extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    TextRepository textRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;
    long id;


    @When("{string} put text named  {string} to {string}")
    public void putTextNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Text> otext=textRepository.findByText(arg1);
        id=otext.get().getId();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg2);
        executePut("http://localhost:8080/api/texts/"+id,jwt,jsonObject.toString());
    }


    @Then("{string} is nameed {string}")
    public void isNameed(String arg0, String arg1) {
            Optional<Text> otext=textRepository.findById(id);
            assertTrue(otext.get().getText().equals(arg1));
    }


    @And("Then last put request statuss is {int}")
    public void thenLastPutRequestStatussIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }

    @When("{string} put textt named  {string} to {string}")
    public void putTexttNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        if(textRepository.findByText(arg0).isPresent()){
            id=textRepository.findByText(arg0).get().getId();
        }
        else{
            Text otext=new Text(arg1);
            textRepository.save(otext);
            id=otext.getId();
        }
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg2);
        executePut("http://localhost:8080/api/texts/"+id,jwt,jsonObject.toString());
    }

    @Then("{string} is not changedd to {string}")
    public void isNotChangeddTo(String arg0, String arg1) {
        assertNotEquals(textRepository.findById(id).get().getText(), arg1);
    }
}

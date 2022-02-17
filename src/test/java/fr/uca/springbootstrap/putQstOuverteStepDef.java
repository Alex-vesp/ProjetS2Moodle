package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class putQstOuverteStepDef extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    QuestionOuverteRepository qstRepository;

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

    @When("{string} put Qst named  {string} to {string}")
    public void putQstNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<QuestionOuverte> oqst=qstRepository.findByText(arg1);
        id=oqst.get().getId();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg2);
        jsonObject.put("reponse",oqst.get().getReponse());
        executePut("http://localhost:8080/api/questionsOuvertes/"+id,jwt,jsonObject.toString());
    }

    @Then("{string} is nammed {string}")
    public void isNammed(String arg0, String arg1) {
        Optional<QuestionOuverte> oqst=qstRepository.findById(id);
        assertTrue(oqst.get().getText().equals(arg1));
    }


    @When("{string} put Qstt named {string} to {string}")
    public void putQsttNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        if(qstRepository.findByText(arg0).isPresent()){
            id=qstRepository.findByText(arg0).get().getId();
        }
        else{
            QuestionOuverte oqst=new QuestionOuverte(arg1,"?");
            qstRepository.save(oqst);
            id=oqst.getId();
        }
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg2);
        jsonObject.put("reponse",qstRepository.findById(id).get().getReponse());
        executePut("http://localhost:8080/api/questionsOuvertes/"+id,jwt,jsonObject.toString());
    }

    @Then("{string} is nott changed to {string}")
    public void isNottChangedTo(String arg0, String arg1) {
        assertNotEquals(qstRepository.findById(id).get().getText(), arg1);
    }

    @And("Then last put request status iss {int}")
    public void thenLastPutRequestStatusIss(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }
}

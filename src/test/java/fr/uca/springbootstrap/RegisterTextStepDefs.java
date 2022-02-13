package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTextStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    TextRepository textRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    AuthController authController;

    long coursid;
    @And("a cours {string} in module {string}")
    public void aCoursInModule(String arg0, String arg1) {
        Cours cours=new Cours(arg0,"fe");
        coursRepository.save(cours);
        coursid=cours.getId();
        Module module= moduleRepository.findByName(arg1).get();
        module.getCours().add(cours);
        moduleRepository.save(module);


    }

    @When("{string} registers text {string} in {string}")
    public void registersTextIn(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg1);
        executeOPost("http://localhost:8080/api/cours/"+coursid+"/texts/",jwt,jsonObject.toString());
    }

    @Then("{string} is registered to {string}")
    public void isRegisteredTo(String arg0, String arg1) throws IOException {
        Cours cours = coursRepository.findById(coursid).get();
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        System.out.println("hahahahah"+content);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        Optional<Text> text = textRepository.findById((long)id);
        assertTrue(text.isPresent());
        for(Text t : cours.getTexts())
        {
            if(t.getId() == id)
                assertEquals(t.getText(),arg0);
        }
    }

    @And("Then last requestt status is {int}")
    public void thenLastRequesttStatusIs(int arg0) {
        assertEquals(this.latestHttpResponse.getStatusLine().getStatusCode(),arg0);
    }

    @Then("{string} is not registered to {string}")
    public void isNotRegisteredTo(String arg0, String arg1) {
    }
}

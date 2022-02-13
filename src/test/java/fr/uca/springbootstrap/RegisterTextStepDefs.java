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
import java.util.HashSet;
import java.util.Optional;

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


    @And("a cours {string} in module {string}")
    public void aCoursInModule(String arg0, String arg1) {
        Cours cours = coursRepository.findByName(arg0).orElse(new Cours(arg0,"destest"));
        coursRepository.save(cours);
        Module module = moduleRepository.findByName(arg1).orElse(new Module(arg1));
        module.getCours().add(cours);
        moduleRepository.save(module);
    }

    @When("{string} registers text {string} in {string}")
    public void registersTextIn(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg1);
        executeOPost("http://localhost:8080/api/cours/"+coursRepository.findByName(arg2).get().getId()+"/texts/",jwt,jsonObject.toString());
    }

    @Then("{string} is registered to {string}")
    public void isRegisteredTo(String arg0, String arg1) throws IOException {
        Cours cours = coursRepository.findByName(arg1).get();
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
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
}

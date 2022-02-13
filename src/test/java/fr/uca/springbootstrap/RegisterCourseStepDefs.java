package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

public class RegisterCourseStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";



    @Autowired
    CoursRepository courseRepository;

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



    @When("{string} registers course {string} in module {string}")
    public void registersCourse(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Module module= moduleRepository.findByName(arg2).get();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",arg1);
        jsonObject.put("des","aaa");
        executeOPost("http://localhost:8080/api/module/"+module.getId()+"/Ressources/cours",jwt,jsonObject.toString());

    }

    @And("{string} is registered to courses")
    public void isRegisteredToCourses(String arg0) throws IOException {
        //lire le resultat de la requete  et recuperer l'ID
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);

        int id=jsonObject.getInt("id");
        Optional<Cours> ocours= courseRepository.findById((long) id);
        assertTrue(ocours.isPresent());
        assertEquals(ocours.get().getName(),arg0);
    }

    @And("Then last request status  is {int}")
    public void thenLastRequestStatusIs(int arg0) throws IOException {

        assertEquals(this.latestHttpResponse.getStatusLine().getStatusCode(),200);

    }

    @Then("{string} is not registered to courses")
    public void isNotRegisteredToCourses(String arg0) {
        assertFalse(courseRepository.findByName(arg0).isPresent());
    }
}

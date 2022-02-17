package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
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

public class PutCourseStepDef extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;
    long id;
    @When("{string} put course named  {string} to {string}")
    public void putCourseNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Cours> ocours=coursRepository.findByName(arg1);
        id=ocours.get().getId();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",arg2);
        jsonObject.put("des","changer");
        executePut("http://localhost:8080/api/cours/"+id,jwt,jsonObject.toString());


    }


    @Then("{string} is named  {string}")
    public void isNamed(String arg0, String arg1) {
        Optional<Cours> ocours=coursRepository.findById(id);
        assertTrue(ocours.get().getName().equals(arg1));
    }


    @And("Then last delete request statusgs is  {int}")
    public void thenLastDeleteRequestStatusgsIs(int arg0) {

    }

    @And("Then last  put  request  status is {int}")
    public void thenLastDeleteRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

    @Then("{string} is not changed to {string}")
    public void isNotChangedTo(String arg0, String arg1) {
    }
}

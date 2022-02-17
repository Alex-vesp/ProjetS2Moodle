package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
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

public class DeleteCourseStepdefs extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    TextRepository textRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CoursRepository courseRepository;

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


    @When("{string} delete  course named  {string} in module {string}")
    public void deleteCourseNamed(String arg0, String arg1, String arg2) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        //supprimer si le module avec ce nom existe déja :
        Optional<Cours> ocourse= courseRepository.findByName(arg1);
        executeDelete("http://localhost:8080/api/cours/"+ocourse.get().getId(),jwt);
    }
     long id;
    @Then("{string} is deleted from courses")
    public void isDeletedFromCourses(String arg0) throws IOException {
        //module qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
         id=jsonObject.getInt("id");
        Optional <Cours> ocours = courseRepository.findById((long) id);
        assertTrue(!ocours.isPresent());
    }

    @And("Then last delete request  status is {int}")
    public void thenLastDeleteRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

    @And("a course named a {string}")
    public void aCourseNamedA(String arg0) {
        Cours cours = courseRepository.findByName(arg0).orElse(new Cours(arg0,"trr"));

        coursRepository.save(cours);
    }

    @Then("{string} is not deleted from courses")
    public void isNotDeletedFromCourses(String arg0) throws IOException {


    }
}

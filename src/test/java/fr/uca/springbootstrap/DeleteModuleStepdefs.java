package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteModuleStepdefs extends SpringIntegration{
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

    @When("{string} delete  module named  {string}")
    public void deleteModuleNamed(String arg0, String arg1) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        //supprimer si le module avec ce nom existe déja :
        Optional<Module> omodule= moduleRepository.findByName(arg1);
        executeDelete("http://localhost:8080/api/module/"+omodule.get().getId(),jwt);
    }

    @Then("{string} is deleted from modules")
    public void isDeletedFromModules(String arg0) throws IOException {
        //module qui a le nom arg0 supprimé :
        Optional<Module> omodule = moduleRepository.findByName(arg0);
        assertTrue(!omodule.isPresent());

        //module qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        omodule = moduleRepository.findById((long) id);
        assertTrue(!omodule.isPresent());
    }

    @And("Then last delete request status is {int}")
    public void thenLastDeleteRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

    @Then("{string} is not deleted from modules")
    public void isNotDeletedFromModules(String arg0) {
    }
}

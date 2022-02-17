package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.repository.CoursRepository;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class putFileStepDef extends SpringIntegration{
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
    @When("{string} put  module named  {string} to {string}")
    public void putModuleNamedTo(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Module> omodule=moduleRepository.findByName(arg1);
        id=omodule.get().getId();
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",arg1);
        executePut("http://localhost:8080/api/module/"+id,jwt,jsonObject.toString());

    }


    @Then("{string} is named {string}")
    public void isNamed(String arg0, String arg1) {
        Optional<Module> omodule=moduleRepository.findById(id);
        assertTrue(omodule.get().getName().equals(arg1));
    }
}

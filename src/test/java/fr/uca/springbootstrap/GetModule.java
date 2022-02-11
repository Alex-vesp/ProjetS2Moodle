package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.payload.request.AddModuleRequest;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetModule  extends SpringIntegration{
    private static final String PASSWORD = "password";

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

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Autowired
    CoursRepository coursRepository;


    @Given("a module with name {string} and id {long}")
    public void aModuleWithNameAndId(String arg0, long arg1) {
        Module module = moduleRepository.findByName(arg0)
                .orElse(new Module(arg0));
        moduleRepository.save(module);
    }

    @When("{string} get the participants of the module of name {string} and id {long}")
    public void getTheParticipantsOfTheModuleOfNameAndId(String arg0, String arg1, long arg2) throws IOException {
        Optional<Module> module = moduleRepository.findById(arg2);
        Module response = new Module();
        if (module.isPresent()){
            response = module.get();
        }
        int id = module.get().getId().intValue();
        executeGet("http://localhost:8080/api/module/" + Integer.toString(id) + "/participants");
    }

    @Then("he can visualize the name of the participants of the module of id {long}")
    public void heCanVisualizeTheNameOfTheParticipantsOfTheModuleOfId(long arg0) {
        Optional<Module> omodule = moduleRepository.findById(arg0);
        assertTrue(omodule.isPresent());
    }


}

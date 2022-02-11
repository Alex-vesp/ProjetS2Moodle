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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCoursStepDefs  extends SpringIntegration{

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

    @And("And a module named {string}")
    public void andAModuleNamed(String arg0) {
        Module module = moduleRepository.findByName(arg0).orElse(new Module(arg0));
        module.setParticipants(new HashSet<>());
        moduleRepository.save(module);
    }

    @And("a cours named {string}")
    public void aCoursNamed(String arg0) {
        Cours cours = coursRepository.findByName(arg0).orElse(new Cours(arg0,"jjjjj"));
        coursRepository.save(cours);
    }

    @When("{string} delete  cours named  {string} in {string}")
    public void deleteCoursNamedIn(String arg0, String arg1, String arg2) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        //supprimer si le module avec ce nom existe déja :
        Optional<Cours> ocours= coursRepository.findByName(arg1);
        executeDelete("http://localhost:8080/api/cours/"+ocours.get().getId(),jwt);

    }

    @Then("{string} is  not registered to cours in {string}")
    public void isNotRegisteredToCoursIn(String arg0, String arg1) {
        Optional<Cours> ocours = coursRepository.findByName(arg0);
        assertTrue(!ocours.isPresent());

    }
}

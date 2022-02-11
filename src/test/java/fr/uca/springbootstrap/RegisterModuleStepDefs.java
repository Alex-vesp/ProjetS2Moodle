package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.ERole;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Role;
import fr.uca.springbootstrap.models.User;
import fr.uca.springbootstrap.payload.request.AddModuleRequest;
import fr.uca.springbootstrap.repository.ModuleRepository;
import fr.uca.springbootstrap.repository.RoleRepository;
import fr.uca.springbootstrap.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterModuleStepDefs  extends SpringIntegration{
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

    @And("a student with login {string}")
    public void aStudentWithLogin(String arg0) {
        User user = userRepository.findByUsername(arg0).
                orElse(new User(arg0, arg0 + "@test.fr", encoder.encode(PASSWORD)));
        user.setRoles(new HashSet<Role>(){{ add(roleRepository.findByName(ERole.ROLE_TEACHER).
                orElseThrow(() -> new RuntimeException("Error: Role is not found."))); }});
        userRepository.save(user);

    }

    @When("{string} registers  module {string}")
    public void registersModule(String arg0, String arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        //supprimer si le module avec ce nom existe déja :
        Optional<Module> omodule= moduleRepository.findByName(arg1);
        if (omodule.isPresent()){
            moduleRepository.delete(omodule.get());
        }
        String obj="{\"name\":\""+arg1+"\"}";
        executeOPost("http://localhost:8080/api/module/",jwt,obj);

    }

    @And("{string} is registered to modules")
    public void isRegisteredToModules(String arg0) {
        Optional<Module> omodule = moduleRepository.findByName(arg0);
        assertTrue(omodule.isPresent());
    }
}

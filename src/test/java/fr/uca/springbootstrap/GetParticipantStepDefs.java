package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
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
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetParticipantStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";
    HttpResponse myHTTPreponse ;

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
    @When("{string} get participants of module {string}")
    public void getParticipantsOfModule(String arg0, String arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        Optional<Module> omodule= moduleRepository.findByName(arg1);
        executeGet("http://localhost:8080/api/module/"+omodule.get().getId()+"/participants",jwt);

    }

    @Then("participants of module {string} are read")
    public void participantsOfModuleAreRead(String arg0) throws IOException {
        //module qui a l'id  supprimé :
        Optional<Module> omodule= moduleRepository.findByName(arg0);
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        //verifie que le nom de l'objet lu  dans la reponse correspoand  a arg0
        assertTrue(omodule.get().getId().equals(id));
    }
    @And("Then last request status is {int}")
    public void thenLastRequestStatusIss(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }
}

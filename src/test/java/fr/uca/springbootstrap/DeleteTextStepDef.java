package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Text;
import fr.uca.springbootstrap.models.User;
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

public class DeleteTextStepDef extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TextRepository textRepository;


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


    @And("a text named {string}")
    public void aTextNamed(String arg0) {
        Text text = textRepository.findByText(arg0).orElse(new Text(arg0));
        textRepository.save(text);
    }

    @When("{string} delete  text named  {string}")
    public void deleteTextNamed(String arg0, String arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        //supprimer si le text avec ce nom existe déja :
        Optional<Text> otext= textRepository.findByText(arg1);
        executeDelete("http://localhost:8080/api/texts/"+otext.get().getId(),jwt);
    }

    @Then("{string} is deleted from texts")
    public void isDeletedFromTexts(String arg0) throws IOException {
        //text qui a le nom arg0 supprimé :
        Optional<Text> otext = textRepository.findByText(arg0);
        assertTrue(!otext.isPresent());
        //text qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        System.out.println(content);
        int id=jsonObject.getInt("id");
        otext = textRepository.findById((long) id);
        assertTrue(!otext.isPresent());
    }
    @And("Then last deletee request status is {int}")
    public void thenLastDeleteeRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }
}

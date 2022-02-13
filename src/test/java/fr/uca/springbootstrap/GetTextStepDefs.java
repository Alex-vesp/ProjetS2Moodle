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
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTextStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";
    HttpResponse myHTTPreponse ;

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



    @When("{string} get text named {string}")
    public void getTextNamed(String arg0, String arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        Optional<Text> otext= textRepository.findByText(arg1);
        executeGet("http://localhost:8080/api/texts/"+otext.get().getId(),jwt);
    }

    @Then("{string} is read from texts")
    public void isReadFromTexts(String arg0) throws IOException {
        //text qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        String name=jsonObject.getString("text");
        //verifie que le nom de l'objet lu  dans la reponse correspoand  a arg0
        assertTrue(name.equals(arg0));
    }

    @And("Then last requestt status iss {int}")
    public void thenLastRequesttStatusIss(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }
}

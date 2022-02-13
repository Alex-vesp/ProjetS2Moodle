package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Text;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteQstOuverteStepDef extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    TextRepository textRepository;

    @Autowired
    QuestionOuverteRepository questionOuverteRepository;

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


    @And("a qstOuverte named {string}")
    public void aQstOuverteNamed(String arg0) {
        QuestionOuverte qst = questionOuverteRepository.findByText(arg0).orElse(new QuestionOuverte(arg0, "A"));
        questionOuverteRepository.save(qst);
    }

    @When("{string} delete qstOuverte named {string}")
    public void deleteQstOuverteNamed(String arg0, String arg1) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        //supprimer si la qst avec ce nom existe déja :
        Optional<QuestionOuverte> oqst= questionOuverteRepository.findByText(arg1);
        executeDelete("http://localhost:8080/api/questionsOuvertes/"+oqst.get().getId(),jwt);
    }

    @Then("{string} is deleted from qstOuvertes")
    public void isDeletedFromQstOuvertes(String arg0) throws IOException {
        //qst qui a le nom arg0 supprimé :
        Optional<QuestionOuverte> oqst = questionOuverteRepository.findByText(arg0);
        assertTrue(!oqst.isPresent());
        //qst qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        System.out.println(content);
        int id=jsonObject.getInt("id");
        oqst = questionOuverteRepository.findById((long) id);
        assertTrue(!oqst.isPresent());
    }

    @And("Then last delete requestt status is {int}")
    public void thenLastDeleteRequesttStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );
    }

    @Then("{string} is not deleted to questionnaire {string}")
    public void isNotDeletedToQuestionnaire(String arg0, String arg1) {
    }
}

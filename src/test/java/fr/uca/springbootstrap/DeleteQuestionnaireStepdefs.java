package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Questionnaire;
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

public class DeleteQuestionnaireStepdefs extends SpringIntegration{
    private static final String PASSWORD = "password";


    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    QuestionnaireRepository questionnairepository;

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


    @When("{string} delete  questionnaire named  {string} in module {string}")
    public void deleteQuestionnaireNamed(String arg0, String arg1, String arg2) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        //supprimer si le module avec ce nom existe déja :
        Optional<Questionnaire> oquestionnaire= questionnairepository.findByName(arg1);

        executeDelete("http://localhost:8080/api/questioonare/"+oquestionnaire.get().getId(),jwt);
    }

    @Then("{string} is deleted from questionnaires")
    public void isDeletedFromQuestionnaires(String arg0) throws IOException {
        //module qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        Optional <Questionnaire> oquestionnaire = questionnairepository.findById((long) id);
        assertTrue(!oquestionnaire.isPresent());
    }





    @And("a questionnaire named aa {string}")
    public void aQuestionnaireNamedAa(String arg0) {
        Questionnaire questionnaire = questionnairepository.findByName(arg0).orElse(new Questionnaire(arg0,"psst"));

        questionnairepository.save(questionnaire);
    }

    @And("Then last  delete request  status is {int}")
    public void thenLastDeleteRequestStatusIs(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }

    @And("a questionnaire named aaa {string}")
    public void aQuestionnaireNamedAaa(String arg0) {
        Questionnaire questionnaire = questionnairepository.findByName(arg0).orElse(new Questionnaire(arg0,"psst"));

        questionnairepository.save(questionnaire);
    }
}

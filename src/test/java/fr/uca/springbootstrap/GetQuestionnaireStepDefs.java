package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Cours;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.Questionnaire;
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

public class GetQuestionnaireStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";
    HttpResponse myHTTPreponse ;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    QuestionnaireRepository questionnairerepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    PasswordEncoder encoder;



    @When("{string} get  questionnaire named  {string} in {string}")
    public void getQuestionnaireNamed(String arg0, String arg1, String arg2) throws IOException {

        String jwt = authController.generateJwt(arg0, PASSWORD);
        User user = userRepository.findByUsername(arg0).get();
        Module module = moduleRepository.findByName(arg2).get();
        Optional<Questionnaire> oquestionnaire= questionnairerepository.findByName(arg1);
        executeGet("http://localhost:8080/api/questionnaire/"+oquestionnaire.get().getId(),jwt);



    }

    @Then("{string} is read from questionnaires")
    public void isReadFromQuestionnaire(String arg0) throws IOException {
        //module qui a l'id  supprimé :
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        String name=jsonObject.getString("name");
        //verifie que le nom de l'objet lu  dans la reponse correspoand  a arg0
        assertTrue(name.equals(arg0));


    }




    @And("a questionnaire named aa {string}")
    public void aQuestionnaireNamedAa(String arg0) {
        Questionnaire questionnaire = questionnairerepository.findByName(arg0).orElse(new Questionnaire(arg0,"trr"));

        questionnairerepository.save(questionnaire);
    }

    @And("Then last   request status iss  {int}")
    public void thenLastRequestStatusIss(int arg0) {
        assertEquals(latestHttpResponse.getStatusLine().getStatusCode(),arg0 );

    }
    }



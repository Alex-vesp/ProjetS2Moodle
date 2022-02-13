package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.*;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.repository.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterQstOuverteStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";
    @Autowired
    QuestionOuverteRepository questionOuverteRepository;

    @Autowired
    QuestionnaireRepository questRepository;

    @Autowired
    TextRepository textRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoursRepository coursRepository;

    @Autowired
    AuthController authController;


    long questID;
    @And("a questionnaire {string} in module {string}")
    public void aQuestionnaireInModule(String arg0, String arg1) {
        Questionnaire questionnaire=new Questionnaire(arg0,"fe");
        questRepository.save(questionnaire);
        questID=questionnaire.getId();
        Module module= moduleRepository.findByName(arg1).get();
        module.getQuestionnaires().add(questionnaire);
        moduleRepository.save(module);

    }

    @When("{string} registers questionOuverte {string} in {string}")
    public void registersQuestionOuverteInIn(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg1);
        jsonObject.put("reponse","heh");
        executeOPost("http://localhost:8080/api/questionnaire/"+questID+"/questionsOuvertes/",jwt,jsonObject.toString());
    }

    @Then("{string} is registered to questionnaire {string}")
    public void isRegisteredToQuestionnaire(String arg0, String arg1) throws IOException {
        Questionnaire quest = questRepository.findById(questID).get();
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        Optional<QuestionOuverte> qstOuverte = questionOuverteRepository.findById((long)id);
        assertTrue(qstOuverte.isPresent());
        for(QuestionOuverte questO : quest.getQsts())
        {
            if(questO.getId() == id)
                assertEquals(questO.getText(),arg0);
        }
    }

    @And("Then last requestt statuss is {int}")
    public void thenLastRequesttStatussIs(int arg0) {
        assertEquals(this.latestHttpResponse.getStatusLine().getStatusCode(),arg0);
    }


}

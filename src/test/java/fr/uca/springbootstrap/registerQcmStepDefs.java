package fr.uca.springbootstrap;

import fr.uca.springbootstrap.controllers.AuthController;
import fr.uca.springbootstrap.models.Module;
import fr.uca.springbootstrap.models.QCM;
import fr.uca.springbootstrap.models.QuestionOuverte;
import fr.uca.springbootstrap.models.Questionnaire;
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

import static org.junit.jupiter.api.Assertions.*;

public class registerQcmStepDefs extends SpringIntegration{
    private static final String PASSWORD = "password";

    @Autowired
    QcmRepository qcmRepository;

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
    @When("{string} registers Qcm {string} in {string}")
    public void registersQcmIn(String arg0, String arg1, String arg2) throws IOException {
        String jwt = authController.generateJwt(arg0, PASSWORD);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",arg1);
        jsonObject.put("reponse","heh");
        executeOPost("http://localhost:8080/api/questionnaire/"+questID+"/Qcm/",jwt,jsonObject.toString());
        System.out.println("http://localhost:8080/api/questionnaire/"+questID+"/Qcm/");
    }

    @Then("qcm {string} is registered to questionnaire {string}")
    public void qcmIsRegisteredToQuestionnaire(String arg0, String arg1) throws IOException {
        Questionnaire quest = questRepository.findById(questID).get();
        HttpEntity entity = latestHttpResponse.getEntity();
        String content = EntityUtils.toString(entity);
        JSONObject jsonObject= new JSONObject(content);
        int id=jsonObject.getInt("id");
        Optional<QCM> qcm = qcmRepository.findById((long)id);
        assertTrue(qcm.isPresent());
    }

    @And("a questionnairee {string} in module {string}")
    public void aQuestionnaireeInModule(String arg0, String arg1) {
        Questionnaire questionnaire=new Questionnaire(arg0,"fe");
        questRepository.save(questionnaire);
        questID=questionnaire.getId();
        Module module= moduleRepository.findByName(arg1).get();
        module.getQuestionnaires().add(questionnaire);
        moduleRepository.save(module);
    }

    @And("Then last requestt statussx is {int}")
    public void thenLastRequesttStatussxIs(int arg0) {
        assertEquals(this.latestHttpResponse.getStatusLine().getStatusCode(),arg0);

    }

    @Then("qcm {string} is  not registered to questionnaire {string}")
    public void qcmIsNotRegisteredToQuestionnaire(String arg0, String arg1) {
        Optional<QCM> oqcm=qcmRepository.findByText(arg0);
        assertFalse(oqcm.isPresent());
    }
}

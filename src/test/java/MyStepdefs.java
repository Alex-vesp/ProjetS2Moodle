import classes.AnswerQCM;
import classes.QCM;
import classes.Question;
import classes.Student;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {
    ArrayList<AnswerQCM>  answers;
    ArrayList<Student> students;
    ArrayList<Question> questions;


    @Given("a student of name {string}")
    public void aStudentOfName(String arg0) {
        //creer un etudiant
        students=new ArrayList<>();
        Student student= new Student(arg0);
        students.add(student);
    }

    @And("a question of id {int}   of type QCM")
    public void aQuestionOfIdOfTypeQCM(int arg0) {
        //creer une question
        questions= new ArrayList<>();
        String[] options= {"reponse 1","reponse 2","reponse 3"};
        int[] correctAnswers={1,2};
        QCM qcm=new QCM(1, options,correctAnswers);
        questions.add(qcm);
    }

    @And("{string} has aldrady answered the question of id {int}")
    public void hasAldradyAnsweredTheQuestionOfId(String arg0, int arg1) {
        //creer une reponse
        answers=new ArrayList<>();
        int[] ans={1};
        AnswerQCM answerqcm=new AnswerQCM(1,arg0,arg1,ans);
        answers.add(answerqcm);
    }

    @When("{string} choose an option {int} for question {int}")
    public void chooseAnOption(String arg0, int arg1,int arg2) {
        int[] ans={arg1};
        answers.add(new AnswerQCM(2,arg0,arg2,ans));
    }

    @Then("There is an answer of {int} value  for question of id {int}  and student {string}  in  answers")
    public void thereIsAnAnswerOfValueForQuestionOfIdAndStudentInAnswers(int arg0, int arg1, String arg2) {
        for (AnswerQCM a:answers){
            if(a.getStudentLogin().equals(arg2)  && a.getQuestionId()==arg1){
                assertEquals(arg0,a.getAnswers()[0]);
            }

        }
    }

}

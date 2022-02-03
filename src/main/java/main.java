import classes.AnswerQCM;
import classes.QCM;
import classes.Question;
import classes.Student;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {
        ArrayList<Student> students=new ArrayList<>();
        ArrayList<Question> questions= new ArrayList<>();
        ArrayList<AnswerQCM>  answers=new ArrayList<>();

        //creer un etudiant
        Student student= new Student("AAA");
        students.add(student);


        //creer une question
        String[] options= {"reponse 1","reponse 2","reponse 3"};
        int[] correctAnswers={1,2};
        QCM qcm=new QCM(1, options,correctAnswers);
        questions.add(qcm);

        //creer une reponse
        int[] ans={1};
        AnswerQCM answerqcm=new AnswerQCM(1,"AAA",1,ans);
        answers.add(answerqcm);
        System.out.println("done");




    }
}

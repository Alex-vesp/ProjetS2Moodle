package classes;

public class AnswerQCM {
    int id=0;
    String studentLogin;
    int questionId;
    int[] answers;
    public AnswerQCM(int id,String studentLogin, int questionId,int[] answers){
        this.id=id;
        this.studentLogin=studentLogin;
        this.questionId=questionId;
        this.answers=answers;
    }

}

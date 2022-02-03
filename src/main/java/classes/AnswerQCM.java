package classes;

public class AnswerQCM {
    public int getId() {
        return id;
    }

    public String getStudentLogin() {
        return studentLogin;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int[] getAnswers() {
        return answers;
    }

    private int id=0;
    private String studentLogin;
    private  int questionId;
    private  int[] answers;
    public AnswerQCM(int id,String studentLogin, int questionId,int[] answers){
        this.id=id;
        this.studentLogin=studentLogin;
        this.questionId=questionId;
        this.answers=answers;
    }

}

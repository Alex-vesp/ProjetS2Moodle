package classes;

public class QCM extends Question{
    String context;
    String[] options ;
    int[] correctAnswers;
    public QCM(int id, String[] options,int[] correctAnswers) {
        super(id);
        this.options=options;
        this.correctAnswers=correctAnswers;
    }


}

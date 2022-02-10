package fr.uca.springbootstrap.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
@Entity
public class QuestionOuverte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String text;
    @NotBlank
    private String reponse;

    public QuestionOuverte(String text, String reponse) {
        this.text = text;
        this.reponse = reponse;
    }

    public QuestionOuverte() {

    }

    @Override
    public String toString(){
        return "\n QuestionOuverte{ question : "+this.text+"reponse : "+this.reponse+" }";
    }
}

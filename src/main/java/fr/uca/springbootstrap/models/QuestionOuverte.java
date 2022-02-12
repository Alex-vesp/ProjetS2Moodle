package fr.uca.springbootstrap.models;

import org.json.JSONObject;

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

    public Long getId() {
        return id;
    }

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
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("id",this.id);
        jsonObject.put("text",this.text);
        jsonObject.put("reponse",this.reponse);
        return jsonObject.toString();
    }
}

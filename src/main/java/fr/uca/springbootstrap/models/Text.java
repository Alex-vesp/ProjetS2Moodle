package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @NotBlank
    private String text;

    public Text(String text) {
        this.text = text;
    }
    public Text() {
    }
    @Override
    public String toString(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",this.text);
        jsonObject.put("id",this.id);
        return jsonObject.toString();
    }

}

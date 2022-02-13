package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(	name = "texts_cours",
            joinColumns = @JoinColumn(name = "Text_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id"))
    private Cours cours;
    public Text(String text) {
        this.text = text;
    }
    public Text() {
    }
    @Override
    public String toString(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("text",this.text);
        jsonObject.put("id",this.id);
        return jsonObject.toString();
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

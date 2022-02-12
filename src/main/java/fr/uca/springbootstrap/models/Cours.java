package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "cours_texts",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "Text_id"))
    private Set<Text> texts;

    public String getName() {
        return name;
    }

    public Set<Text> getTexts() {
        return texts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setTexts(Set<Text> texts) {
        this.texts = texts;
    }

    public String getDes() {
        return des;
    }

    @NotBlank
    private String name;
    @NotBlank
    private String des;
    public Cours(String name, String des) {
        this.name=name;
    this.des=des;}
    public Cours(String name) {
        this.name=name;
}

    public Long getId() {
        return id;
    }

    public Cours() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",this.name);
        jsonObject.put("des",this.des);
        jsonObject.put("id",this.id);

        return jsonObject.toString();
    }
}

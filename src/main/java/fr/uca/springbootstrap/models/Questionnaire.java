package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(	name = "Questionnaire")
public class Questionnaire {

    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String des;

    public Set<QuestionOuverte> getQsts() {
        return qsts;
    }

    public void setQsts(Set<QuestionOuverte> qsts) {
        this.qsts = qsts;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "questionnaire_QuestionOuverte",
            joinColumns = @JoinColumn(name = "Questionnaire_id"),
            inverseJoinColumns = @JoinColumn(name = "QuestionOuverte_id"))
    private Set<QuestionOuverte> qsts;

    public Questionnaire(String name, String des) {
        this.name=name;
        this.des=des;}

    public Questionnaire() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
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

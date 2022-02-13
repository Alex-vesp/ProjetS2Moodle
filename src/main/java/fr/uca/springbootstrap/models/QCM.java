package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class QCM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }



    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "Qcm_prop",
            joinColumns = @JoinColumn(name = "QCM_id"),
            inverseJoinColumns = @JoinColumn(name = "Proposition_id"))
    private Set<Proposition> propositions;

    public Set<Proposition> getPropositions() {
        return propositions;
    }

    @NotBlank
    private String text;
    @NotBlank
    private String reponse;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getReponse() {
        return reponse;
    }

    public QCM(String text, String reponse) {
        this.text = text;
        this.reponse = reponse;
    }

    public QCM() {

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

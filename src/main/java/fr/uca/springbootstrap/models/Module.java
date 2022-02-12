package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(	name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "user_modules",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "modules_cours",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id"))
    private Set<Cours> cours;

    public Set<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "modules_questionnaires",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "questionnaire_id"))
    private Set<Questionnaire> questionnaires;

    public Set<Cours> getCours() {
        return cours;
    }

    public Module() {
    }

    public Module(String name) {
        this.name = name;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name",this.name);
        jsonObject.put("id",this.id);
        return jsonObject.toString();
    }
}

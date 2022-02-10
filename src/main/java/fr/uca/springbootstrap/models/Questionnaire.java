package fr.uca.springbootstrap.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(	name = "Questionnaire")
public class Questionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String des;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "questionnaire_qst",
            joinColumns = @JoinColumn(name = "Questionnaire_id"),
            inverseJoinColumns = @JoinColumn(name = "QST_id"))
    private Set<QST> qsts;

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
}

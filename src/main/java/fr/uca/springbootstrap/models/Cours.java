package fr.uca.springbootstrap.models;

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

    public Cours() {
    }
}

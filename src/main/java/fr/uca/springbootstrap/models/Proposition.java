package fr.uca.springbootstrap.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
@Entity
public class Proposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    @NotBlank
    private String text;
    public Proposition(){

    }
    public Proposition(String text){
        this.text=text;

    }
}

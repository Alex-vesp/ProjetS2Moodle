package fr.uca.springbootstrap.models;

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

    @NotBlank
    private String text;

    public Text(String text) {
        this.text = text;
    }
    public Text() {
    }
    @Override
    public String toString(){
        return "Text{"+"id="+this.id+",text="+this.text+"}";
    }

}

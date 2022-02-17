package fr.uca.springbootstrap.models;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;

@Entity
@Table(	name = "Responses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Responses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 120)
    String ResponseText;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(	name = "Qst_Rep",
            joinColumns = @JoinColumn(name = "Responses_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private User usr;
    public Responses(){

    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public Responses(String ResponseText){

        this.ResponseText = ResponseText;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String toString(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("reptext",this.ResponseText);
        jsonObject.put("id",this.id);
        return jsonObject.toString();
    }
}

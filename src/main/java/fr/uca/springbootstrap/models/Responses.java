package fr.uca.springbootstrap.models;

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
    @NotBlank
    @Size(max = 120)
    String ResponseText;
    @NotBlank
    int GoodResponseOrNot;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(	name = "Responses_list",
            joinColumns = @JoinColumn(name = "Responses_id"),
            inverseJoinColumns = @JoinColumn(name = "QST_id"))
    private QST qst;
    public Responses(){

    }
    public Responses(String ResponseText, int GoodResponseOrNot){

        this.ResponseText = ResponseText;
        this.GoodResponseOrNot = GoodResponseOrNot;

    }
}

package fr.uca.springbootstrap.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Entity
    @Table(	name = "QST",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class QST {



    enum Type {
        QCM,
        OPEN,
        CODERUNNER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 120)
    String qstText;
    Type qstType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(	name = "qst_qestionnaire",
            joinColumns = @JoinColumn(name = "QST_id"),
            inverseJoinColumns = @JoinColumn(name = "Questionnaire_id"))
    private Questionnaire questionnaire;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(	name = "Responses_list",
            joinColumns = @JoinColumn(name = "QST_id"),
            inverseJoinColumns = @JoinColumn(name = "Responses_id"))
    private Set<Responses> responses;

    public QST() {

    }
    public QST(int id, String qstText,Type qstType) {
        this.qstText = qstText;
        this.qstType = qstType;
    }


}

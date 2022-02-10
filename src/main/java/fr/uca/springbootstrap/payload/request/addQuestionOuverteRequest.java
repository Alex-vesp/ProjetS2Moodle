package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class addQuestionOuverteRequest {

    @NotBlank
    private String text;
    @NotBlank
    private String reponse;

    public String getText() {
        return text;
    }


    public String getReponse() {
        return reponse;
    }
}

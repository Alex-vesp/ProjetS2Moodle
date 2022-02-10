package fr.uca.springbootstrap.payload.request;

import fr.uca.springbootstrap.models.QST;

import javax.validation.constraints.NotBlank;

public class AddQstRequest {
    @NotBlank
    private String qstText;
    private  String qstType;

    public String getQstType() {
        return qstType;
    }

    public String getQstText() {
        return qstText;
    }

    public void setQstText(String qstText) {
        this.qstText = qstText;
    }

}

package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class addTextRequest {
    @NotBlank
    private String text ;

    public String getText() {
        return text;
    }
}

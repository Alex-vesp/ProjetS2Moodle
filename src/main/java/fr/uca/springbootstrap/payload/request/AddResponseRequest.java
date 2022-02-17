package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class AddResponseRequest {

    String ResponseText;

    public String getResponseText() {
        return ResponseText;
    }

    public void setResponseText(String responseText) {
        ResponseText = responseText;
    }
}

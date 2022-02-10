package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class AddRessourceRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String des;

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }
}

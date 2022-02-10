package fr.uca.springbootstrap.payload.request;

import javax.validation.constraints.NotBlank;

public class AddModuleRequest {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }
}

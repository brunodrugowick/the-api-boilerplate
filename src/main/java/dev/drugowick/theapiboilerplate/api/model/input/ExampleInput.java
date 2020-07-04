package dev.drugowick.theapiboilerplate.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ExampleInput {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}

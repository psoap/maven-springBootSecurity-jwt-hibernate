package net.psoap.newsportal.model.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
//@NoArgsConstructor
//@Builder
public class UserForm {
    @Email(message = "error.email.incorrect-input")
    @Size(min = 6, max = 48, message = "error.email.incorrect-size")
    private String email;
    @Size(min = 6, max = 16, message = "error.password.incorrect-size")
    private String password;
}
package net.psoap.newsportal.service;

import net.psoap.newsportal.model.form.UserForm;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity registration(final UserForm userForm);
    ResponseEntity login(final UserForm userForm);
}
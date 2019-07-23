package net.psoap.newsportal.controller;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.model.form.UserForm;
import net.psoap.newsportal.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "${rest.api.uri}"+"users",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    @PostMapping("/reg")
    @CrossOrigin
    public ResponseEntity registration(@RequestBody @Valid UserForm userForm){
        return userService.registration(userForm);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserForm userForm){
        return userService.login(userForm);
    }
}
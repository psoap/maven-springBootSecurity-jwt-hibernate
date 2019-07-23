package net.psoap.newsportal.service.impl;

import net.psoap.newsportal.controller.exception.ApiError;
import net.psoap.newsportal.dao.UserDao;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.enums.UserRole;
import net.psoap.newsportal.model.enums.UserState;
import net.psoap.newsportal.model.form.UserForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import net.psoap.newsportal.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    UserDao userDao;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenProvider tokenProvider;
    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Registration success")
    void registrationSuccess(){
        UserForm userForm = new UserForm();
        userForm.setEmail("junit@mail.com");


        User userForChecking = User.builder()
                .email(userForm.getEmail())
                .userRole(UserRole.USER)
                .userState(UserState.NEW)
                .build();

        when(userDao.existUserByEmail(userForChecking.getEmail())).thenReturn(false);

        ResponseEntity responseEntity = userService.registration(userForm);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        verify(userDao, times(1)).insert(userForChecking);
    }

    @Test
    @DisplayName("Registration failed")
    void registrationFailed(){
        UserForm userForm = new UserForm();
        userForm.setEmail("junit@mail.com");

        doReturn(true).when(userDao).existUserByEmail(userForm.getEmail());

        ResponseEntity responseEntity = userService.registration(userForm);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(((ApiError) responseEntity.getBody()).getKeys(), Collections.singletonList("error.email.already-used"));

        verify(userDao, times(0)).insert(ArgumentMatchers.any(User.class));
    }

    @Test
    @DisplayName("Login success")
    void loginSuccess(){
        UserForm userForm = new UserForm();
        userForm.setEmail("junit@mail.com");

        User userForChecking = User.builder()
                .email(userForm.getEmail())
                .userRole(UserRole.USER)
                .userState(UserState.ACTIVE)
                .build();

        UserDetails userDetails = new UserDetailsImpl(userForChecking);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        when(authenticationManager.authenticate(ArgumentMatchers.any(AbstractAuthenticationToken.class)))
                .thenReturn(ArgumentMatchers.any(Authentication.class));
        when(authenticationManager.authenticate(ArgumentMatchers.any(AbstractAuthenticationToken.class)).getPrincipal())
                .thenReturn(userDetails);

//        doReturn(userDetails).when(authenticationManager).authenticate(authentication).getPrincipal();
        doReturn(ArgumentMatchers.anyString()).when(tokenProvider).generateToken(ArgumentMatchers.any(AbstractAuthenticationToken.class));

        ResponseEntity responseEntity = userService.login(userForm);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(String.valueOf(responseEntity.getBody()), ArgumentMatchers.anyString());
        verify(userDao, times(1)).findUserByEmail(userForm.getEmail());
    }

}
package net.psoap.newsportal.service.impl;

import lombok.RequiredArgsConstructor;
import net.psoap.newsportal.controller.exception.ApiError;
import net.psoap.newsportal.dao.UserDao;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.enums.UserRole;
import net.psoap.newsportal.model.enums.UserState;
import net.psoap.newsportal.model.form.UserForm;
import net.psoap.newsportal.security.UserDetailsImpl;
import net.psoap.newsportal.security.jwt.JwtTokenProvider;
import net.psoap.newsportal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @PreAuthorize("isAnonymous()")
    public ResponseEntity registration(UserForm userForm) {
        if (userDao.existUserByEmail(userForm.getEmail())) {
            return new ResponseEntity<>(new ApiError("error.email.already-used"), HttpStatus.CONFLICT);
        }

        User newUser = User.builder()
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .userRole(UserRole.USER)
                .userState(UserState.NEW)
                .build();
        userDao.insert(newUser);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("isAnonymous()")
    public ResponseEntity login(UserForm userForm) {
        try {
            String email = userForm.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, userForm.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (!userDetails.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("error.account.inactive.new"));
            }

            String token = jwtTokenProvider.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}

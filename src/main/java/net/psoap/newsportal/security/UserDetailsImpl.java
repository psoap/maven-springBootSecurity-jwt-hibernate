package net.psoap.newsportal.security;

import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.model.enums.UserRole;
import net.psoap.newsportal.model.enums.UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private UserState userState;
    private UserRole userRole;

    public UserDetailsImpl(User user){
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        userState = user.getUserState();
        userRole = user.getUserRole();
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority(userRole.name())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userState.equals(UserState.ACTIVE);
    }
}

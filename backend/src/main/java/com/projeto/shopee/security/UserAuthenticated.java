package com.projeto.shopee.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.projeto.shopee.dto.LoginRequestDTO;

public class UserAuthenticated implements UserDetails {

    private final String login;
    private final String password;
    private final Long userId;

    public UserAuthenticated(LoginRequestDTO loginRequestDTO, Long userId) {
        this.login = loginRequestDTO.getUsername();
        this.password = loginRequestDTO.getPassword();
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
        return true;
    }

    public Long getUserId() {
        return userId;
    }
}

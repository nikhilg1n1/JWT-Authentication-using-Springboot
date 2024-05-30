package com.jwtauthentication.service;

import com.jwtauthentication.entities.UserInfo;
import com.jwtauthentication.entities.UserRole;
import com.jwtauthentication.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.loader.ast.internal.CollectionElementLoaderByIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class CustomUserService extends UserInfo implements UserDetails {

    public String username;

    public String password;

    Collection<? extends GrantedAuthority> authorities;
    public CustomUserService(UserInfo userInfo){
        this.username=userInfo.getUsername();
        this.password=userInfo.getPassword();
        List<GrantedAuthority> auths= new ArrayList<>();

        for(UserRole userRole:userInfo.getUserRoles()){
            auths.add(new SimpleGrantedAuthority(userRole.getName()));
        }
        this.authorities=auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

package com.spingwithbushan.journalapp.service;

import com.spingwithbushan.journalapp.entity.User;
import com.spingwithbushan.journalapp.repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositry userRepositry;

    @Autowired
    public UserDetailsServiceImpl(UserRepositry userRepositry) {
        this.userRepositry = userRepositry;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepositry.findByUserName(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        List<GrantedAuthority> authorities = (u.getRoles() == null ? List.<String>of() : u.getRoles())
                .stream()
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r) // ensure ROLE_ prefix
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUserName())
                .password(u.getPassword()) // must be the encoded hash from DB
                .authorities(authorities)
                .build();
    }
}

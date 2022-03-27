package com.sistem.blog.security;

import com.sistem.blog.model.Rol;
import com.sistem.blog.model.User;
import com.sistem.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado :  " + username ));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRols(user.getRols()));
    }

    private Collection< ? extends GrantedAuthority> mapRols(Set<Rol> rols) {
        return rols.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
    }
}
package com.sistem.blog.controllers;

import com.sistem.blog.DTO.LoginDTO;
import com.sistem.blog.DTO.RecordDTO;
import com.sistem.blog.model.Rol;
import com.sistem.blog.model.User;
import com.sistem.blog.repository.RolRepository;
import com.sistem.blog.repository.UserRepository;
import com.sistem.blog.security.JWTAuthResponseDTO;
import com.sistem.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){

        Authentication authentication =
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.genToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody RecordDTO recordDTO){

        if(userRepository.existsByUsername(recordDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(recordDTO.getUsername());
        user.setPassword(passwordEncoder.encode(recordDTO.getPassword()));

        Rol roles = rolRepository.findByName("ROL_USER").get();
        user.setRols(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
    }
}
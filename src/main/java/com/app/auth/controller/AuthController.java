package com.app.auth.controller;

import com.app.auth.model.Role;
import com.app.auth.model.RoleType;
import com.app.auth.model.User;
import com.app.auth.payloads.ApiResponse;
import com.app.auth.payloads.JwtAuthenticationResponse;
import com.app.auth.payloads.LoginRequest;
import com.app.auth.payloads.SignupRequest;
import com.app.auth.repository.RoleRepository;
import com.app.auth.repository.UserRepository;
import com.app.auth.service.CustomUserDetail;
import com.app.auth.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1.0/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello From Spring Security";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String getToken(){
        return null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUserName(signupRequest.getUserName())){
            return new ResponseEntity<>(new ApiResponse(false,"Username is Already taken"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<>(new ApiResponse(false, "Email is already in use"), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(signupRequest.getUserName());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setMobile(signupRequest.getMobile());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Role userrole = roleRepository.findByRoleName(RoleType.ROLE_ADMIN).orElseThrow(
                ()->new NoSuchElementException("Role not found"));
        user.setRoles(Collections.singleton(userrole));
        user.setCreatedAt(new Date());
        user.setEnable(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setUpdatedAt(new Date());
        User usrResult = userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!!"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getUserName()+" "+ loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Set<String> roles = customUserDetail.getAuthorities().stream()
                .map(item-> item.getAuthority()).collect(Collectors.toSet());
        return ResponseEntity.ok(new JwtAuthenticationResponse(customUserDetail.getId(),jwt,
                customUserDetail.getUsername(),customUserDetail.getFirstName(),
                customUserDetail.getLastName(), customUserDetail.getEmail(), customUserDetail.getMobile(), roles));
    }
}

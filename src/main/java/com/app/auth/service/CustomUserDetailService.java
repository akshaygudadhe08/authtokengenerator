package com.app.auth.service;

import com.app.auth.model.User;
import com.app.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()-> new UsernameNotFoundException("User detail not found for "+userName)
        );
        return CustomUserDetail.createUser(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with id "+id));
        return CustomUserDetail.createUser(user);
    }

    @Transactional
    public UserDetails loadUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User Not found with email "+email)
        );
        return CustomUserDetail.createUser(user);
    }
}

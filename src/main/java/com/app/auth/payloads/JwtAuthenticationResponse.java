package com.app.auth.payloads;

import java.util.List;
import java.util.Set;

public class JwtAuthenticationResponse {
    private Long id;
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Set<String> roles;

    public JwtAuthenticationResponse(){}
    public JwtAuthenticationResponse(Long id,String accessToken,String userName,
                                     String firstName,String lastName,String email,String mobile, Set<String> roles){

        this.id = id;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

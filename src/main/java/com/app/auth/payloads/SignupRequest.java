package com.app.auth.payloads;

import java.util.Set;

public class SignupRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    //private Set<String> roles;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
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

    /*public void setRoles(Set<String> roles) {
        this.roles = roles;
    }*/

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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

    /*public Set<String> getRoles() {
        return roles;
    }*/
}

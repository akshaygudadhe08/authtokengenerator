package com.app.auth.service;

import com.app.auth.email.VerificationToken;
import com.app.auth.model.User;

public interface UserServiceInterface {
    User getUser(String verificationToken);
    void createVerificationToken(User user, String token);
    VerificationToken generateVerificationToken(String verificationToken);
    public VerificationToken getVerificationToken(String verificationTOken);
    String validateVerificationToken(String token);
}

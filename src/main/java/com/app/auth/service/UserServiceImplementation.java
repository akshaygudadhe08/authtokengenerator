package com.app.auth.service;

import com.app.auth.email.VerificationToken;
import com.app.auth.email.VerificationTokenRepository;
import com.app.auth.model.User;
import com.app.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImplementation implements UserServiceInterface{

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Override
    public User getUser(String verificationToken) {
        final VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if(token != null)
            return token.getUser();
        return null;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateVerificationToken(String verificationToken) {
        VerificationToken vToken = verificationTokenRepository.findByToken(verificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = verificationTokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null)
            return TOKEN_INVALID;
        final User user = verificationToken.getUser();
        final Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <=0){
            verificationTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }
        user.setEnable(true);
        userRepository.save(user);
        return TOKEN_VALID;
    }


}

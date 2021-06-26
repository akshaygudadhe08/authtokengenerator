package com.app.auth.events.listener;

import com.app.auth.events.OnRegistrationCompleteEvent;
import com.app.auth.model.User;
import com.app.auth.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    UserServiceInterface userServiceInterface;

    @Autowired
    MessageSource messageSource;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    Environment environment;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent onRegistrationCompleteEvent){
        final User user = onRegistrationCompleteEvent.getUser();
        final String token = UUID.randomUUID().toString();
        userServiceInterface.createVerificationToken(user, token);
        final SimpleMailMessage email = constructEmailMessage(onRegistrationCompleteEvent, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user,
                                                    final String token){
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/v1.0/api/auth/registrationConfirm?token=" + token;
        final String message = messageSource.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}

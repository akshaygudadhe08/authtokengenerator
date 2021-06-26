package com.app.auth.events;

import com.app.auth.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl){
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

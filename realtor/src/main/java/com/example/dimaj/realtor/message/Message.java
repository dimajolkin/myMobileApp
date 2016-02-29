package com.example.dimaj.realtor.message;

import com.example.dimaj.realtor.models.UserProfile;

/**
 * Created by dimaj on 29.02.2016.
 */
public class Message {
    protected String text;
    protected UserProfile user;
    protected boolean status = true;

    protected int id;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

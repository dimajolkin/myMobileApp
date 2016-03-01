package com.example.dimaj.realtor.modules.messages;

import com.example.dimaj.realtor.models.UserProfile;

import java.util.ArrayList;

public class Messages {
    protected UserProfile owner;
    protected ArrayList<UserProfile> peers;

    public Messages() {
        this.peers = new ArrayList<UserProfile>();
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }
}

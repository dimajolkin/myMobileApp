package com.example.dimaj.myapplication.components.notice;

import com.example.dimaj.myapplication.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class OneNoticeContent {
    protected String title;
    protected String icon;
    protected int hidden;
    protected UserProfile owner;
    protected OneNoticeMessage message;

    public OneNoticeContent(JSONObject json) {

        try {
            title = json.getString("title");
            icon = json.getString("icon");
            hidden = json.getInt("hidden");
            owner = new UserProfile(json.getJSONObject("owner"));
            message = new OneNoticeMessage(json.getJSONObject("message"));
        } catch (JSONException ex) {
            return;
        }


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }

    public OneNoticeMessage getMessage() {
        return message;
    }

    public void setMessage(OneNoticeMessage message) {
        this.message = message;
    }
}

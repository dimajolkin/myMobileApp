package com.example.dimaj.realtor.components.notice;

import com.example.dimaj.realtor.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class OneNoticeContent {
    protected String title;
    protected String icon;
    protected boolean hidden = false;
    protected UserProfile owner;
    protected OneNoticeMessage message;

    public OneNoticeContent(JSONObject json) {

        try {
            if (json.has("title"))
                title = json.getString("title");
            if (json.has("icon"))
                icon = json.getString("icon");
//@todo - fix server type int|boolean
//            if (json.has("hidden")) {
//                String h = json.getString(json.getString("hidden"));
//            }
//                hidden = json.getBoolean("hidden");

            JSONObject dataOwner = json.getJSONObject("owner");
            owner = new UserProfile(dataOwner);
            message = new OneNoticeMessage(json.getJSONObject("message"));
        } catch (JSONException ex) {
            ex.printStackTrace();
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

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
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

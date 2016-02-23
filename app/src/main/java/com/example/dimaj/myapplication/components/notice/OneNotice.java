package com.example.dimaj.myapplication.components.notice;
import com.example.dimaj.myapplication.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class OneNotice {
    protected String type;
    protected OneNoticeContent content;

    public OneNotice(JSONObject json) {
        try {
            type = json.getString("type");
            content = new OneNoticeContent(json.getJSONObject("content"));

        } catch (JSONException ex) {
            return;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OneNoticeContent getContent() {
        return content;
    }

    public void setContent(OneNoticeContent content) {
        this.content = content;
    }

    public OneNoticeMessage getMessage() {
        if (content != null) {
            content.getMessage();
        }

        return null;
    }

    public UserProfile getOwner() {
        if (content != null) {
            content.getOwner();
        }
        return null;
    }

}





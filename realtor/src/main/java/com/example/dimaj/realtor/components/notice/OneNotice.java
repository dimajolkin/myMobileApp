package com.example.dimaj.realtor.components.notice;
import com.example.dimaj.realtor.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class OneNotice {
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_CONTACTS = "contacts";
    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_POST = "post";

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





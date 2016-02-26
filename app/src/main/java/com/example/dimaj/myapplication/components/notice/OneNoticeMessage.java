package com.example.dimaj.myapplication.components.notice;

import org.json.JSONException;
import org.json.JSONObject;

public class OneNoticeMessage {
    protected String text = "";

    public OneNoticeMessage(JSONObject json) {
        try {
            text = json.getString("text");
        } catch (JSONException ex) {
            return;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

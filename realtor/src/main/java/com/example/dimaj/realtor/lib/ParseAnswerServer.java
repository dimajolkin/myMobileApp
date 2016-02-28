package com.example.dimaj.realtor.lib;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseAnswerServer {
    public JSONObject obj = null;

    protected boolean status;

    public boolean getStatus() {
        return this.status;
    }

    public ParseAnswerServer(String json) {
        try {
            this.obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        exclude();
    }

    protected void exclude() {
        try {

            this.status = obj.getString("status").equals("ok");

        } catch (JSONException ex) {
            this.status = false;
        }

    }


    public String getMessage() {
        try {
            return obj.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}

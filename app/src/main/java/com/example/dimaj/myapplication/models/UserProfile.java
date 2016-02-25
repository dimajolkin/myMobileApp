package com.example.dimaj.myapplication.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UserProfile {
    protected int id;
    protected String name;
    protected String avatar;
    protected String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public Bitmap getBitmapAvatar() {

        try {
            URL url = new URL(avatar);
            InputStream is = url.openConnection().getInputStream();
            return BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserProfile(JSONObject json) {
        setAttributes(json);
    }
    public UserProfile() {
    }

    public void setAttributes(JSONObject data) {
        try {
            id = data.getInt("id");
            name = data.getString("name");
            avatar = data.getString("avatar");
            JSONObject jsonBackground = data.getJSONObject("background");
            background = jsonBackground.getString("url");

        } catch (JSONException ex) {
            return;
        }
    }
}

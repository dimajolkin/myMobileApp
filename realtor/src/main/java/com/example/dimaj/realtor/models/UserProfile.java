package com.example.dimaj.realtor.models;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.dimaj.realtor.components.LoadImages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProfile {

    protected static ArrayList<UserProfile> users = new ArrayList<UserProfile>();

    public static UserProfile find(int id) {
        for (UserProfile user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void add(UserProfile user) {
        users.add(user);
    }

    protected int id;
    protected String name;
    protected String avatarUrl;
    protected String backgroundUrl;
    protected Bitmap avatar;
    protected Bitmap background;

    private Runnable onAfterInitAvatar = null;
    private Runnable onAfterInitBackgroud = null;

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

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Bitmap getBackground() {
        return background;
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
            avatarUrl = data.getString("avatar");


            final LoadImages load = new LoadImages();
            load.setUrl(avatarUrl);
            load.setRunnable(new Runnable() {
                @Override
                public void run() {
                    avatar = load.getBitmap();
                }
            });
            load.execute();

            if (data.has("background")) {
                JSONObject jsonBackground = data.getJSONObject("background");
                backgroundUrl = jsonBackground.getString("url");
                final LoadImages load2 = new LoadImages();
                load2.setUrl(backgroundUrl);
                load2.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        background = load2.getBitmap();
                    }
                });
                load2.execute();
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
            return;
        }
        //add current user in storage
        users.add(this);
    }

    public void setOnAfterInitAvatar(Runnable onAfterInitAvatar) {
        this.onAfterInitAvatar = onAfterInitAvatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setOnAfterInitBackgroud(Runnable onAfterInitBackgroud) {
        this.onAfterInitBackgroud = onAfterInitBackgroud;
    }
}

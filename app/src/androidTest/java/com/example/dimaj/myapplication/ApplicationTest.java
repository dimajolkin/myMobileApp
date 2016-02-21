package com.example.dimaj.myapplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.test.ApplicationTestCase;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.lib.ParseAnswerServer;
import com.example.dimaj.myapplication.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testParseAuthTrue() {
        JSONObject connect = new JSONObject();
        try {
            connect.put("status", "ok");
            connect.put("token", "12345678");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        ParseAnswerServer parse = new ParseAnswerServer(connect.toString());
        assertTrue(parse.getStatus());
    }

    public void testParseAuthFalse() {
        JSONObject connect = new JSONObject();
        try {
            connect.put("status", "error");
            connect.put("token", "12345678");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        ParseAnswerServer parse = new ParseAnswerServer(connect.toString());

        assertFalse(parse.getStatus());
    }

    public void testParseUserProfile() {
        JSONObject data = new JSONObject();
        try {
            data.put("id", 23);
            data.put("name", "Дима");
            data.put("avatar", "avatar");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        UserProfile user = new UserProfile();
        user.setAttributes(data);

        assertEquals(23, user.getId());
        assertEquals("Дима", user.getName());
        assertEquals("avatar", user.getAvatar());
    }

    public void testLoadAvatar() {
        String url = "http://static.realtor.im/st1/zghkzwn5/1449658357566807f5f2e1b.jpeg";
        UserProfile user = new UserProfile();
        user.setAvatar(url);
        Bitmap bmp = user.getBitmapAvatar();

        assertTrue(bmp != null);
    }

}
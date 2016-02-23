package com.example.dimaj.myapplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.test.ApplicationTestCase;

import com.example.dimaj.myapplication.lib.ParseAnswerServer;
import com.example.dimaj.myapplication.components.notice.OneNotice;
import com.example.dimaj.myapplication.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONObject createJSONNotice() {
        JSONObject data = new JSONObject();
        try {
            data.put("type", "contact");
            data.put("content", new JSONObject()
                            .put("title", "Append friend")
                            .put("icon", "icon")
                            .put("hidden", 0)
                            .put("owner", new JSONObject()
                                    .put("id", 1)
                                    .put("avatar", "avatar")
                                    .put("name", "dima")
                                    .put("url", "/user/1")
                                    .put("sex", 0))
                            .put("message", new JSONObject()
                                    .put("text", "Hello World"))
            );

            return data;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return  null;
    }

    public void testParseOneNotice() {
        JSONObject json  = createJSONNotice();
        OneNotice notice = new OneNotice(json);


        assertTrue(notice.getContent() != null);
        assertTrue(notice.getContent().getMessage() != null);
        assertEquals("Hello World", notice.getContent().getMessage().getText());

    }

}
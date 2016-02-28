package com.example.dimaj.realtor;

import android.app.Application;
import android.graphics.Bitmap;
import android.test.ApplicationTestCase;

import com.example.dimaj.realtor.lib.ParseAnswerServer;
import com.example.dimaj.realtor.components.notice.OneNotice;
import com.example.dimaj.realtor.models.UserProfile;

import org.json.JSONArray;
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
    public void testParseMessage() {
        String msg  = "[{\"type\":\"message\",\"content\":{\"title\":\"\\u041e\\u0442\\u043f\\u0440\\u0430\\u0432\\u0438\\u043b \\u0441\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435\",\"icon\":\"sf sf-mail\",\"hidden\":false,\"owner\":{\"id\":577,\"avatar\":\"http:\\/\\/static.realtor.im\\/st1\\/zgljzwnl\\/145441715156b0a4ff6e6bf.jpg\",\"name\":\"\\u0414\\u0436\\u043e\\u0440\\u0430\\u0445 \\u0418\\u0437\\u0430\\u043d\\u0434\\u0430\\u043b\\u043e\\u0432\",\"url\":\"\\/user\\/577\",\"sex\":1},\"message\":{\"is_system\":false,\"model\":\"dialog\",\"peer\":577,\"text\":\"send\",\"id\":23,\"date_create\":\"18:53\"},\"sourse\":{\"countUnReadTalk\":1,\"countUnRead\":13}}}]";
        try {
            JSONArray list = new JSONArray(msg);
            for (int i = 0; i < list.length(); i++) {
                JSONObject obj = list.getJSONObject(i);
                OneNotice notice = new OneNotice(obj);

                assertEquals("http://static.realtor.im/st1/zgljzwnl/145441715156b0a4ff6e6bf.jpg", notice.getContent().getOwner().getAvatarUrl());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void testParseOneNotice() {
        JSONObject json  = createJSONNotice();
        OneNotice notice = new OneNotice(json);


        assertTrue(notice.getContent() != null);
        assertTrue(notice.getContent().getMessage() != null);
        assertEquals("Hello World", notice.getContent().getMessage().getText());

    }

}
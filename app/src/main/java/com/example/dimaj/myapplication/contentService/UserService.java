package com.example.dimaj.myapplication.contentService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dimaj.myapplication.activity.app.IndexActivity;
import com.example.dimaj.myapplication.activity.LoginActivity;
import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.lib.Request;
import com.example.dimaj.myapplication.lib.RequestCallback;
import com.example.dimaj.myapplication.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {
    public String TAG = "RunActivity.TEST";
    protected AppCompatActivity activity;
    protected static SharedPreferences storage;
    protected String phpSessid;
    protected static Request request = null;
    private static UserProfile profile = null;

    public String getSession() {
        return phpSessid;
    }

    public UserService(AppCompatActivity activity) {
        this.activity = activity;
        storage = activity.getSharedPreferences("PHPSESSID", 0);
        phpSessid = storage.getString("PHPSESSID", "");

        if (profile == null) {
            profile = new UserProfile();
        }

        if (request == null) {
            request = new Request(Config.URL_INFO);
            if (!phpSessid.isEmpty()) {
                request.addCookie("PHPSESSID", phpSessid);
            }
        }
    }


    public UserProfile getProfile() {
        return profile;
    }

    public void init() {
        request.onRequest(new RequestCallback() {
            @Override
            public void onRequest(Request request) {
                if (!request.isStatus()) {
                    Log.d("FAILED", request.getMessage());
                    return;
                }
                try {
                    JSONObject json = new JSONObject(request.getHtml());
                    if (json.getBoolean("isAuth")) {
                        Log.d(TAG, "пользователь авторизован");
                        if (!activity.getLocalClassName().equals(IndexActivity.class)) {
                            Log.d(TAG, json.toString());
                            JSONObject profileProperty = json.getJSONObject("profile");
                            profile.setAttributes(profileProperty);

                            Intent intent = new Intent(activity, IndexActivity.class);
                            activity.startActivity(intent);

                        }
                    } else {
                        Log.d(TAG, "пользователь не авторизован");
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        });
        request.execute();
    }

}

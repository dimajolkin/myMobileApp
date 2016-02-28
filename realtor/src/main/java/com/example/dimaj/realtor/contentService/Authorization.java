package com.example.dimaj.realtor.contentService;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dimaj.realtor.config.Config;
import com.example.dimaj.realtor.lib.Request;
import com.example.dimaj.realtor.lib.RequestCallback;
import com.example.dimaj.realtor.models.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class Authorization {
    public String TAG = "RunActivity.TEST";
    protected Context context;
    protected static SharedPreferences storage;
    protected String phpSessid;
    protected Request request = null;
    private static UserProfile profile = null;
    private Runnable onAfterSuccessAuth = null;
    private Runnable onAfterFailedAuth = null;

    public String getSession() {
        return phpSessid;
    }

    public Authorization(Context context) {
        this.context = context;
        storage = context.getSharedPreferences("PHPSESSID", 0);
        phpSessid = storage.getString("PHPSESSID", "");

        if (profile == null) {
            profile = new UserProfile();
        }

        request = new Request(Config.URL_INFO);
        if (!phpSessid.isEmpty()) {
            request.addCookie("PHPSESSID", phpSessid);
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
//                        if (!context.getLocalClassName().equals(IndexActivity.class)) {
                        Log.d(TAG, json.toString());
                        JSONObject profileProperty = json.getJSONObject("profile");
                        profile.setAttributes(profileProperty);

                        if (onAfterSuccessAuth != null) {
                            onAfterSuccessAuth.run();
                        }
//                        Intent intent = new Intent(context, IndexActivity.class);
//                        context.startActivity(intent);
                    } else {
                        Log.d(TAG, "пользователь не авторизован");
                        if (onAfterFailedAuth != null) {
                            onAfterFailedAuth.run();
                        }
//                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        });
        request.execute();
    }

    public Authorization onAfterSuccessAuth(Runnable run) {
        this.onAfterSuccessAuth = run;
        return this;
    }

    public Authorization onAfterFaildeAuth(Runnable run) {
        this.onAfterFailedAuth = run;
        return this;
    }


}

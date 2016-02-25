package com.example.dimaj.myapplication.contentService;

import android.os.AsyncTask;

import com.example.dimaj.myapplication.lib.Request;
import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.lib.RequestCallback;

public class AutorizationService {
    protected RequestCallback callback;
    protected Request request = new Request(Config.URL_AUTH);

    public AutorizationService(String login, String password) {
        request.setPost("User[login]", login);
        request.setPost("User[password]", password);
    }

    public void onAfterAuth(RequestCallback callBack) {
        this.callback = callBack;
    }

    public void execute() {
        request.onRequest(this.callback);
        request.execute();
    }

}

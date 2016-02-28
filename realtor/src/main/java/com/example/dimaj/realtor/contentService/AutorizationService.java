package com.example.dimaj.realtor.contentService;

import com.example.dimaj.realtor.lib.Request;
import com.example.dimaj.realtor.config.Config;
import com.example.dimaj.realtor.lib.RequestCallback;

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

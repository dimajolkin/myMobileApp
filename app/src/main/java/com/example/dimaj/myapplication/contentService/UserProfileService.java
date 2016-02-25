package com.example.dimaj.myapplication.contentService;

import android.os.AsyncTask;

import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.lib.Request;
import com.example.dimaj.myapplication.lib.RequestCallback;

public class UserProfileService extends AsyncTask<Void, Void, Void> {
    protected RequestCallback callback;
    protected Request request = new Request(Config.URL_INFO);

    public void onAfterAuth(RequestCallback callBack) {
        this.callback = callBack;
    }

    @Override
    protected Void doInBackground(Void... params) {
//         request.execute();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
//        if (this.callback != null) {
//
//            this.callback.setRequest(this.request);
//            this.callback.run();
//        }
        super.onPostExecute(aVoid);
    }
}

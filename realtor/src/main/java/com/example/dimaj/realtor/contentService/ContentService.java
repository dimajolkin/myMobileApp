package com.example.dimaj.realtor.contentService;

import android.os.AsyncTask;

import com.example.dimaj.realtor.config.Config;
import com.example.dimaj.realtor.lib.Request;
import com.example.dimaj.realtor.lib.RequestCallback;

public class ContentService extends AsyncTask<Void, Void, Void> {
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

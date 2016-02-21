package com.example.dimaj.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.dimaj.myapplication.lib.Request;

class Authorization extends AsyncTask<Void, Void, Void> {
    protected String _result;
    protected TextView text;

    protected Request request = new Request("http://moydom.intero.company/login");

    public void setText(TextView text) {
        this.text = text;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        text.setText("Begin");
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Request request = new Request("http://192.168.0.20:8080");
        request.setPost("User[login]", "dimajolkin@rambler.ru");
        request.setPost("User[password]", "1234567890");
//        request.execute();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        text.setText(_result);
    }
}
package com.example.dimaj.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.lib.Request;
import com.example.dimaj.myapplication.lib.RequestCallback;
import com.example.dimaj.myapplication.service.auth.UserService;

import org.json.JSONException;
import org.json.JSONObject;

public class RunActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);


    }

    @Override
    protected void onResume() {

        super.onResume();
        UserService service =  new UserService(this);
        service.init();


    }

}

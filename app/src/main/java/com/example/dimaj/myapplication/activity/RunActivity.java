package com.example.dimaj.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.contentService.UserService;

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

package com.example.dimaj.realtor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.activity.app.IndexActivity;
import com.example.dimaj.realtor.contentService.Authorization;

public class RunActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);


    }

    @Override
    protected void onResume() {

        Authorization service = new Authorization(this);
        service.onAfterSuccessAuth(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RunActivity.this, IndexActivity.class);
                RunActivity.this.startActivity(i);
            }
        }).onAfterFaildeAuth(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RunActivity.this, LoginActivity.class);
                RunActivity.this.startActivity(i);
            }
        }).init();

        super.onResume();
    }

}

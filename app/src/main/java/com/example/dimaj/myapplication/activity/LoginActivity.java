package com.example.dimaj.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.lib.BaseActivity;
import com.example.dimaj.myapplication.lib.Request;
import com.example.dimaj.myapplication.lib.RequestCallback;
import com.example.dimaj.myapplication.contentService.AutorizationService;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    protected TextView login;
    protected TextView password;
    protected Button button;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (TextView) findViewById(R.id.login);
        password = (TextView) findViewById(R.id.password);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        String TAG = "RunActivity.TEST";
        Log.d(TAG, "RUN CLICK");
        AutorizationService service = new AutorizationService(
                login.getText().toString(),
                password.getText().toString()
        );

        service.onAfterAuth(new RequestCallback() {
            @Override
            public void onRequest(Request request) {
                String TAG = "RunActivity.TEST";
                Log.d(TAG, request.getHtml());
                if (request.isStatus()) {
                    //авторизовался
                    storage = getSharedPreferences("PHPSESSID",MODE_PRIVATE);
                    SharedPreferences.Editor ed = storage.edit();
                    ed.putString("PHPSESSID", request.getSession());
                    ed.apply();
//                    startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                } else {
                    //error
                    Log.d(TAG, request.getMessage());
                    Toast toast = Toast.makeText(getApplicationContext(),
                            request.getMessage(),
                            Toast.LENGTH_SHORT);
                    toast.show();
//                }
                }

            }
        });

        service.execute();

    }
}

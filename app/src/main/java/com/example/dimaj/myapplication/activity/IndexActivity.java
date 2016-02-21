package com.example.dimaj.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.activity.app.NotificationActivity;
import com.example.dimaj.myapplication.components.LoadImages;
import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.models.UserProfile;
import com.example.dimaj.myapplication.service.auth.UserService;
import com.example.dimaj.myapplication.service.auth.notice.NoticeService;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected String token;
    protected UserProfile profile;
    private UserService service = null;
    private NoticeService noticeService = null;

    protected WebSocketClient mWebSocketClient;
    protected ListView listNotices;


    public void setString(String token) {
        this.token = token;
    }

    @Override
    protected void onStart() {
        super.onStart();

//        name.setText(profile.getName());
        listNotices = (ListView) findViewById(R.id.listNotices);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new UserService(this);

        noticeService = new NoticeService(this);
        profile = service.getProfile();


        //addd new element for listNotice
        // находим список
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        //установка имени и аватарки
        TextView text = (TextView) header.findViewById(R.id.name);
        text.setText(profile.getName());

        LoadImages loadImage = new LoadImages();
        loadImage.setUrl(profile.getAvatar());
        loadImage.setIv((ImageView) header.findViewById(R.id.avatar));
        loadImage.execute();

        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            this.connectWebSocket();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            noticeService.send("Hello World");
        } else if (id == R.id.nav_slideshow) {
            //добавление новго оповещения в список
            View item1 = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null);
            listNotices.addHeaderView(item1);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void connectWebSocket() {
        URI url = null;
        JSONObject connect = new JSONObject();
        try {
//            url = new URI("ws://5.149.203.95:8047/notify");
            url = new URI(Config.WEB_SOCKET);

            connect.put("type", "auth");
            connect.put("token", service.getSession());

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        } catch (JSONException ex) {
            ex.printStackTrace();

        }


        final String connectString = connect.toString();

        mWebSocketClient = new WebSocketClient(url) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                Log.d("Websocket", connectString);
                mWebSocketClient.send(connectString);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray list = new JSONArray(message);
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject obj = list.getJSONObject(i);
                                String status = obj.getString("type");
                                if (status.equals("error")) {
                                    Log.d("Websocker", "reconnect => " + message);
                                    mWebSocketClient.send(connectString);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Websocker", message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}

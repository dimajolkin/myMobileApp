package com.example.dimaj.realtor.activity.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.activity.app.pages.MessagesFragment;
import com.example.dimaj.realtor.activity.app.pages.NoticeFragment;
import com.example.dimaj.realtor.activity.app.pages.SettingFragment;
import com.example.dimaj.realtor.activity.app.pages.UserFragment;
import com.example.dimaj.realtor.models.UserProfile;
import com.example.dimaj.realtor.contentService.Authorization;
import com.example.dimaj.realtor.service.RealtorNoticeService;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NoticeFragment.OnFragmentInteractionListener {

    protected String token;
    protected UserProfile profile;
    private Authorization service = null;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.d("newIntent", "run");
        if (extras != null) {
            if (extras.containsKey("page")) {
                int page = extras.getInt("page", R.id.nav_wall);
                setPage(page);
            } else {
                Log.d("newIntent", "page not found");
            }
        } else {
            Log.d("newIntent", "data not found");
        }
    }


    //    protected ListView listNotices;
//    protected ArrayList<OneNotice> notices;


    public void setString(String token) {
        this.token = token;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //firs start notice service
        startService(new Intent(this, RealtorNoticeService.class));

        //addd new element for listNotice
        // находим список
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);

        service = new Authorization(this);
        profile = service.getProfile();

        //set default page
        setPage(R.id.nav_wall);

        //установка имени и аватарки
        TextView text = (TextView) header.findViewById(R.id.name);
        text.setText(profile.getName());
        final ImageView avatar = (ImageView) header.findViewById(R.id.avatar);

        avatar.setImageBitmap(profile.getAvatar());

        navigationView.setNavigationItemSelectedListener(this);
        onNewIntent(getIntent());
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
        setPage(id);
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

        setPage(id);
        if (id == R.id.nav_notice) {
            //todo
        } else if (id == R.id.nav_wall) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setPage(int id) {
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_notice) {
            NoticeFragment fragment = new NoticeFragment();
            setTitle("Уведомления");
            ft.replace(R.id.content, fragment).commit();

        } else if (id == R.id.nav_wall) {
            UserFragment fragment = new UserFragment(service);
            setTitle("Стена");
            ft.replace(R.id.content, fragment).commit();

        } else if (id == R.id.nav_message) {
            MessagesFragment fragment = new MessagesFragment(service);
            setTitle("Сообщения");
            ft.replace(R.id.content, fragment).commit();

        } else if (id == R.id.action_settings) {
            SettingFragment fragment = new SettingFragment(service);
            ft.replace(R.id.content, fragment).commit();
            setTitle("Настройки");
        }

    }


}

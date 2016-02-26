package com.example.dimaj.myapplication.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.activity.app.IndexActivity;
import com.example.dimaj.myapplication.components.notice.OneNotice;
import com.example.dimaj.myapplication.contentService.UserService;
import com.example.dimaj.myapplication.contentService.notice.NoticeService;

import org.json.JSONObject;

public class RealtorNoticeService extends Service {
    public static  NoticeService  service;
    public static boolean isInit = false;
    SharedPreferences sharedPreferences;

    public RealtorNoticeService() {
    }

    final String LOG_TAG = "RealtorNoticeService";

    public void onCreate() {
        super.onCreate();
        sharedPreferences = (SharedPreferences)getSharedPreferences("PHPSESSID", 0);
            service = new NoticeService(this);
            service.setNotificationManager((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
            Log.d(LOG_TAG, "Create NoticeService");

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        try {
            if (!isInit) {
                if (service.getSession().isEmpty()) {
                    String tokken = sharedPreferences.getString("PHPSESSID", "");
                    service.setSession(tokken);
                }

                service.initWebSocket();

                Log.d(LOG_TAG, "Create Init");
                isInit = true;
            }
        } catch (Exception ex) {
            Log.d(LOG_TAG, ex.getMessage());
        }


        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        service.stop();

        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    class MyTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            UserService userService = new UserService(RealtorNoticeService.this);
            userService.init();

            return null;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void someTask() {

//        NotificationManager notificationmanager = (NotificationManager)service.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent notificationIntent = new Intent(service, IndexActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, notificationIntent, 0);
//
//            long when=System.currentTimeMillis();
//            Notification.Builder builder = new Notification.Builder(service);
//            builder.setContentIntent(pendingIntent);
//            builder.setAutoCancel(true);
//            builder.setSmallIcon(R.drawable.ic_menu_camera);
//            builder.setWhen(when);
//            builder.setTicker("Notification");
//            builder.setContentTitle("Title");
//            builder.setContentText("Content");
//
//            Notification notification = builder.build();
//            notificationManager.notify(32, notification);
    }
}

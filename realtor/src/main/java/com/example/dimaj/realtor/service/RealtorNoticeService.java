package com.example.dimaj.realtor.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.example.dimaj.realtor.contentService.Authorization;
import com.example.dimaj.realtor.contentService.notice.NoticeFactory;

public class RealtorNoticeService extends Service {
    public static NoticeFactory service;



    public RealtorNoticeService() {
    }

    final String LOG_TAG = "RealtorNoticeService";

    public void onCreate() {
        super.onCreate();
        service = new NoticeFactory(this);
        service.setNotificationManager((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        Log.d(LOG_TAG, "Create NoticeFactory");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        new MyTask().execute();

        return super.onStartCommand(intent, flags, startId);
    }

    class MyTask extends AsyncTask<Void,Void ,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            RealtorNoticeService.this.runAuth();
            return null;
        }
    }

    protected void runAuth() {
        final Authorization auth = new Authorization(this);
        auth.onAfterSuccessAuth(new Runnable() {
            @Override
            public void run() {
                if (!auth.getSession().isEmpty()) {
                    service.setSession(auth.getSession());
                    service.setOnStop(new Runnable() {
                        @Override
                        public void run() {
                            //@todo stop
                        }
                    });
                    service.initWebSocket();

                    return;
                }
                RealtorNoticeService.this.stopSelf();
            }
        }).onAfterFaildeAuth(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Service failed auth");
                RealtorNoticeService.this.stopSelf();
            }
        }).init();
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

}
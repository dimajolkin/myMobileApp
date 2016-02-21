package com.example.dimaj.myapplication.service.auth.notice;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.activity.app.NotificationActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NoticeService {

    protected AppCompatActivity activity;
    protected NotificationCompat.Builder mBuilder;
    protected static int number = 0;


    public NoticeService(AppCompatActivity activity) {
        this.activity = activity;
        mBuilder = new NotificationCompat.Builder(activity);
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void send(String text)
    {
        Intent intent = new Intent(activity, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(activity, 0, intent, 0);

        Notification n  = new Notification.Builder(activity)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
//                .addAction(R.drawable.ic_menu_camera, "Call", pIntent)
//                .addAction(R.drawable.ic_menu_camera, "More", pIntent)
//                .addAction(R.drawable.ic_menu_camera, "And more", pIntent).build();

        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}

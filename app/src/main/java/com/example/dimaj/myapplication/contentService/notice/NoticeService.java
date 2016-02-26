package com.example.dimaj.myapplication.contentService.notice;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.activity.app.IndexActivity;
import com.example.dimaj.myapplication.components.LoadImages;
import com.example.dimaj.myapplication.components.notice.OneNotice;
import com.example.dimaj.myapplication.components.notice.OneNoticeContent;
import com.example.dimaj.myapplication.components.notice.OneNoticeMessage;
import com.example.dimaj.myapplication.config.Config;
import com.example.dimaj.myapplication.models.UserProfile;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NoticeService {

    protected AppCompatActivity activity;
    protected Service service;
    protected NotificationCompat.Builder mBuilder;
    protected static int number = 0;
    protected String session = "";
    protected boolean statusConnect = false;
    protected WebSocketClient mWebSocketClient;
    protected Timer timer;

    protected NoticeServiceFactotySystemNotice systemNotice;

    protected NotificationManager notificationManager;

    protected Thread thread;

    public Thread getThread() {
        return thread;
    }

    public void stop() {
        thread = null;
        mWebSocketClient.close();
        timer.cancel();

    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public NoticeService(Service service) {
        this.service = service;
        systemNotice = new NoticeServiceFactotySystemNotice();

    }


    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return this.session;
    }
    public void initWebSocket() {

        if (statusConnect) {
            return;
        }
        systemNotice = new NoticeServiceFactotySystemNotice();

        URI url = null;
        JSONObject connect = new JSONObject();
        try {
            url = new URI(Config.WEB_SOCKET);
            connect.put("type", "auth");
            connect.put("token", session);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        } catch (JSONException ex) {
            ex.printStackTrace();

        }

        final String connectString = connect.toString();

        timer = new Timer();

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

                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Websocker", "new " + message);
                            JSONArray list = new JSONArray(message);
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject obj = list.getJSONObject(i);
                                String status = obj.getString("type");
                                if (status.equals("error")) {
                                    Log.d("Websocker", "reconnect => " + message);
                                    statusConnect = false;
                                    mWebSocketClient.send(connectString);
                                    continue;
                                }

                                statusConnect = true;
                                try {
                                    NoticeService.this.send(new OneNotice(obj));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("Websocker", message);
                    }
                });

                thread.run();

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                statusConnect = false;
                Log.d("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                statusConnect = false;
                Log.d("Websocket", "Error " + e.getMessage());
            }
        };

        mWebSocketClient.connect();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mWebSocketClient != null) {
                    Log.d("TEST", systemNotice.life());
                    mWebSocketClient.send(systemNotice.life());

                }
            }
        }, 5000, 10000);


    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void send(final OneNotice notice) {
//        String text = "Текст не найден",
//                title = "Заголовок не найден";
//
//        OneNoticeContent content = notice.getContent();
//        OneNoticeMessage msg = notice.getContent().getMessage();
//        UserProfile owner = content.getOwner();
//        text = msg.getText();
//        title = content.getTitle();
//        //clear html
//        text = Html.fromHtml(text).toString();
//
//        LoadImages loadImage = new LoadImages();
//        loadImage.setUrl(owner.getAvatar());
//        Bitmap icon = loadImage.download();


//        NotificationManager notificationmanager = (NotificationManager)service.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(service, IndexActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, notificationIntent, 0);

            long when=System.currentTimeMillis();
            Notification.Builder builder = new Notification.Builder(service);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.drawable.ic_menu_camera);
            builder.setWhen(when);
            builder.setTicker("Notification");
            builder.setContentTitle("Title");
            builder.setContentText("Content");

            Notification notification = builder.build();
            notificationManager.notify(32, notification);
    }

    class NoticeServiceThread extends AsyncTask<Void, Void, Void> {
        private OneNotice notice;

        public void setNotice(OneNotice notice) {
            this.notice = notice;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected Void doInBackground(Void... params) {
//            NotificationManager notificationmanager = (NotificationManager)service.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(service, IndexActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, notificationIntent, 0);

            String text = "Текст не найден",
                    title = "Заголовок не найден";

            OneNoticeContent content = notice.getContent();
            OneNoticeMessage msg = notice.getContent().getMessage();
            UserProfile owner = content.getOwner();

            text = msg.getText();
            title = content.getTitle();

            //clear html
            text = Html.fromHtml(text).toString();

            LoadImages loadImage = new LoadImages();
            loadImage.setUrl(owner.getAvatar());
            Bitmap icon = loadImage.download();

            Notification n = new Notification.Builder(service)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_menu_send)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(icon).build();
//
            if (notificationManager != null) {
                notificationManager.notify(number++, n);
            }

//                .addAction(R.drawable.ic_menu_camera, "Call", pIntent)
//                .addAction(R.drawable.ic_menu_camera, "More", pIntent)
//                .addAction(R.drawable.ic_menu_camera, "And more", pIntent).build();


            return null;
        }
    }
}


class NoticeServiceFactotySystemNotice {
    protected String life = "";

    public String life() {
        if (life == null) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("type", "life");
                life = obj.toString();
            } catch (JSONException ex) {
                return "";
            }
        }

        return life;
    }
}
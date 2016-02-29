package com.example.dimaj.realtor.contentService.notice;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.activity.app.IndexActivity;
import com.example.dimaj.realtor.components.LoadImages;
import com.example.dimaj.realtor.components.notice.OneNotice;
import com.example.dimaj.realtor.config.Config;
import com.example.dimaj.realtor.models.notice.OneNoticeBuilderFactory;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;


public class NoticeFactory {

    protected AppCompatActivity activity;
    protected Service service;
    protected static int number = 0;
    protected String session = "";
    protected boolean statusConnect = false;
    protected WebSocketClient mWebSocketClient;
    protected Runnable onStop;
    protected Timer timer;

    protected NoticeServiceFactotySystemNotice systemNotice;

    protected NotificationManager notificationManager;

    protected Thread thread;


    public void stop() {
        thread = null;
        mWebSocketClient.close();
        timer.cancel();
    }


    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void setOnStop(Runnable onStop) {
        this.onStop = onStop;
    }

    public NoticeFactory(Service service) {
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
            int countError = 0;

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
                                    if (countError > 10) {
                                        NoticeFactory.this.stop();

                                        if (NoticeFactory.this.onStop != null) {
                                            NoticeFactory.this.onStop.run();
                                        }
                                    }
                                    Log.d("Websocker", "reconnect => " + message);
                                    statusConnect = false;
                                    countError++;
                                    mWebSocketClient.send(connectString);
                                    continue;
                                }

                                statusConnect = true;
                                try {
                                    NoticeFactory.this.send(new OneNotice(obj));
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
                if (NoticeFactory.this.onStop != null) {
                    NoticeFactory.this.onStop.run();
                }
            }
        };

        mWebSocketClient.connect();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mWebSocketClient != null) {
                    Log.d("Websocker", systemNotice.life());
                    mWebSocketClient.send(systemNotice.life());

                }
            }
        }, 5000, 10000);


    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void send(final OneNotice notice) {

        Log.d("NoticeFactory", notice.getContent().getOwner().getAvatarUrl());

        final LoadImages loadImage = new LoadImages();
        loadImage.setUrl(notice.getContent().getOwner().getAvatarUrl());
        loadImage.setRunnable(new Runnable() {
            @Override
            public void run() {
                notice.getContent().getOwner().setAvatar(loadImage.getBitmap());

                Intent notificationIntent = new Intent(service, IndexActivity.class)
                        .putExtra("page", R.id.nav_notice);

                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(service, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new OneNoticeBuilderFactory(notice, service).result();
                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();
                notificationManager.notify(number++, notification);
            }
        });
        loadImage.execute();
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
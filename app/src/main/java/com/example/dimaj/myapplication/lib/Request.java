package com.example.dimaj.myapplication.lib;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dimaj.myapplication.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Request extends AsyncTask<Void, Void, Void> {

    final String SAM_CSRF = "670e728e0b320f61c783127cdcd7c75a5e1a0103";

    protected String session;
    protected String url;
    protected String html;
    public String message;
    public RequestCallback reguestCallback;
    public boolean status;

    public String getMessage() {
        return message;
    }

    protected JSONArray response;

    protected final static BasicCookieStore cookieStore = new BasicCookieStore();

    protected HashMap<String, String> postDataParams = new HashMap<String, String>();

    public Request(String http) {
        url = http;
    }

    public boolean isStatus() {
        return status;
    }

    public void setPost(String name, String value) {
        postDataParams.put(name, value);
    }

    public void addCookie(String name, String value) {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setDomain(Config.DOMAIN);
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            addCookie("SAM_CSRF", SAM_CSRF);

            HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            for (String key : postDataParams.keySet()) {
                String value = postDataParams.get(key);
                pairs.add(new BasicNameValuePair(key, value));
            }
            pairs.add(new BasicNameValuePair("SAM_CSRF", SAM_CSRF));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);
            html = EntityUtils.toString(response.getEntity());
            Log.d("TEST", "html:" + html);
            ParseAnswerServer parse = new ParseAnswerServer(html);
            if (!parse.getStatus()) {
                this.status = false;
                this.message = parse.getMessage();
                return null;
            }

            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header head : headers) {
                Log.d("TEST", head.getValue());
                if (head.getValue().contains("PHPSESSID")) {
                    session = findSession(head.getValue());

                    addCookie("PHPSESSID", session);
                }
            }
            this.status = true;
        } catch (Exception ex) {
            this.status = false;
            this.message = ex.getMessage();
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (reguestCallback != null) {
            reguestCallback.onRequest(this);
        }
    }

    public void onRequest(RequestCallback reg) {
        this.reguestCallback = reg;
    }


    protected String findSession(String patch) {
        Pattern p = Pattern.compile("(PHPSESSID=(?<name>[0-9a-z]+))");
        Matcher m = p.matcher(patch);

        // System.out.println("count groups = "+m.groupCount());
        while (m.find()) {
            String res = m.group();
            if (res.contains("PHPSESSID")) {
                String[] data = res.split("=");
                return data[1];
            }

        }

        return "";
    }

    public String getSession() {
        return session;
    }

    public String getHtml() {
        return html;
    }
}

package com.example.dimaj.realtor.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadImages extends AsyncTask<String, Void, Bitmap> {
    protected Runnable runnable = null;
    protected String imageUrl;
    protected Bitmap bitmap;


    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }

    public Bitmap download() {
        try {
            Log.d("Download image", imageUrl);
            URL url = new URL(imageUrl);
            InputStream is = url.openConnection().getInputStream();
            return BitmapFactory.decodeStream(is);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // TODO Auto-generated method stub
        bitmap =  download();
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (runnable != null) {
            runnable.run();
        }

    }

}

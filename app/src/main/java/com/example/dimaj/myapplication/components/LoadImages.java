package com.example.dimaj.myapplication.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadImages extends AsyncTask<String, Void, Bitmap> {
    protected ImageView iv;
    protected String imageUrl;
    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // TODO Auto-generated method stub

        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openConnection().getInputStream();
            Bitmap bitMap = BitmapFactory.decodeStream(is);
            return bitMap;

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
    protected void onPostExecute(Bitmap result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        iv.setImageBitmap(result);
    }

}

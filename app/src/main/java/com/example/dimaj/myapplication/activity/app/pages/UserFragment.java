package com.example.dimaj.myapplication.activity.app.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimaj.myapplication.R;
import com.example.dimaj.myapplication.components.LoadImages;
import com.example.dimaj.myapplication.contentService.UserService;


public class UserFragment extends Fragment {

    protected  UserService service;

    @SuppressLint("ValidFragment")
    public UserFragment(UserService service) {
        this.service = service;
    }

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_app_user, container, false);

        ImageView img = (ImageView) v.findViewById(R.id.backgroud);
        TextView text = (TextView) v.findViewById(R.id.user_text);

        text.setText("Hello My user Page");

        LoadImages loadImage2 = new LoadImages();
        loadImage2.setUrl(service.getProfile().getBackground());
        loadImage2.setIv(img);
        loadImage2.execute();

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}

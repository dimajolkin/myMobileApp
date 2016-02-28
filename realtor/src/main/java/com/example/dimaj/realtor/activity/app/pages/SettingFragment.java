package com.example.dimaj.realtor.activity.app.pages;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.contentService.Authorization;
import com.example.dimaj.realtor.service.RealtorNoticeService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    private Authorization service = null;
    public Button start;
    public Button stop;

    public SettingFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SettingFragment(Authorization service) {
        this.service = service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_app_setting, container, false);
        initComponents(v);
        return v;
    }

    public void initComponents(View v) {
        start = (Button) v.findViewById(R.id.btn_start);
        stop = (Button) v.findViewById(R.id.btn_stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_start) {
            getActivity().startService(new Intent(getActivity(), RealtorNoticeService.class));

        } else if (v.getId() == R.id.btn_stop) {
            getActivity().stopService(new Intent(getActivity(), RealtorNoticeService.class));
        }
    }
}

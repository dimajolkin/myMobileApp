package com.example.dimaj.realtor.activity.app.pages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.contentService.Authorization;
import com.example.dimaj.realtor.message.Message;
import com.example.dimaj.realtor.message.MessageListAdapter;
import com.example.dimaj.realtor.models.UserProfile;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements View.OnClickListener {

    protected ArrayList<Message> messages;
    protected Authorization service;
    protected UserProfile user;

    public MessagesFragment(Authorization service) {
        // Required empty public constructor
        this.service = service;
        this.user = service.getProfile();
    }

    public MessagesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        init(v);
        return v;
    }


    protected ListView listMessage;
    protected ImageButton btnSend;
    protected EditText text;

    public void init(View v) {
        messages = new ArrayList<Message>();

        listMessage = (ListView) v.findViewById(R.id.listMessage);
        MessageListAdapter listMessageAdapter = new MessageListAdapter(getContext(), messages);
        listMessage.setAdapter(listMessageAdapter);

        btnSend = (ImageButton) v.findViewById(R.id.sendMsg);
        btnSend.setOnClickListener(this);

        text = (EditText) v.findViewById(R.id.test_message);

    }


    @Override
    public void onClick(View v) {
        Message msg = new Message();
        msg.setUser(user);
        msg.setText(text.getText().toString());
        messages.add(msg);
        listMessage.invalidateViews();
    }
}

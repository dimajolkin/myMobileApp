package com.example.dimaj.realtor.message;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.activity.app.pages.MessagesFragment;
import com.example.dimaj.realtor.components.notice.OneNotice;

import java.util.ArrayList;


public class MessageListAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> values;

    public MessageListAdapter(Context context, ArrayList<Message> items) {
        super(context, R.layout.item_message_owner, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Message model = values.get(position);

        View view;
        if (model.isStatus()) {
            view = inflater.inflate(R.layout.item_message_owner, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_message_peer, parent, false);
        }

        TextView title = (TextView) view.findViewById(R.id.one_notice_title);
        title.setText(model.getText());

        TextView message = (TextView) view.findViewById(R.id.one_notice_message);
        message.setText(model.getText());

        return view;
    }
}

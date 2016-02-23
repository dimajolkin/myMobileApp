package com.example.dimaj.myapplication.components.notice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dimaj.myapplication.R;

import java.util.ArrayList;

public class OneNoticeListAdapter extends ArrayAdapter<OneNotice>{
    private final Context context;
    private final ArrayList<OneNotice> values;

    public OneNoticeListAdapter(AppCompatActivity context,  ArrayList<OneNotice> items) {
        super(context, R.layout.notice_page_item, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notice_page_item, parent, false);
        OneNotice notice = values.get(position);


        TextView title =(TextView)view.findViewById(R.id.one_notice_title);
        title.setText(notice.getContent().getTitle());

        TextView message =(TextView)view.findViewById(R.id.one_notice_message);
        message.setText(notice.getMessage().getText());


        return view;
    }
}

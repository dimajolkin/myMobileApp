package com.example.dimaj.realtor.modules.messages.components;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.components.notice.OneNotice;

import java.util.ArrayList;

public class ListEntityDialogAdapter extends ArrayAdapter<EntityDialog> {
    private final Context context;
    private final ArrayList<EntityDialog> values;

    public ListEntityDialogAdapter(AppCompatActivity context,  ArrayList<EntityDialog> items) {
        super(context, R.layout.notice_page_item, items);
        this.context = context;
        this.values = items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notice_page_item, parent, false);
        EntityDialog notice = values.get(position);

//        TextView title =(TextView)view.findViewById(R.id.one_notice_title);
//        title.setText(notice.getContent().getTitle());
//        TextView message =(TextView)view.findViewById(R.id.one_notice_message);
//        message.setText(notice.getMessage().getText());

        return view;
    }
}

package com.example.dimaj.realtor.models.notice;


import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;

import com.example.dimaj.realtor.R;
import com.example.dimaj.realtor.components.notice.OneNotice;
import com.example.dimaj.realtor.components.notice.OneNoticeContent;
import com.example.dimaj.realtor.components.notice.OneNoticeMessage;
import com.example.dimaj.realtor.lib.ImageHelper;
import com.example.dimaj.realtor.models.UserProfile;

public class OneNoticeBuilderFactory {

    private OneNotice notice = null;

    private Notification.Builder builder;


    public OneNoticeBuilderFactory(OneNotice notice, Context context) {
        this.notice = notice;
        builder = new Notification.Builder(context);
//        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
    }


    protected void standard() {
        String text, title;

        OneNoticeContent content = notice.getContent();
        OneNoticeMessage msg = notice.getContent().getMessage();

        UserProfile owner = content.getOwner();

        Bitmap bitmap = owner.getAvatar();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        builder.setLargeIcon(ImageHelper.getRoundedCornerBitmap(resizedBitmap, 60));

        text = msg.getText();
        title = content.getTitle();
        //clear html
        text = Html.fromHtml(text).toString();

        builder.setSmallIcon(R.drawable.ic_stat_comment);
        builder.setTicker("Notification");
        builder.setContentTitle(title);
        builder.setContentText(text);
    }

    /**
     * Формирование сообщения из чата
     */
    protected void message() {
        builder.setSmallIcon(R.drawable.ic_stat_message);
    }

    protected void comment() {
        builder.setSmallIcon(R.drawable.ic_stat_comment);
    }

    protected void voice() {
        builder.setSmallIcon(R.drawable.ic_stat_voice_up_down);
    }
    protected void answer() {
        builder.setSmallIcon(R.drawable.ic_stat_question_answer);
    }

    protected void contacts() {
        builder.setSmallIcon(R.drawable.ic_stat_contact);
    }


    protected void init() {
        standard();
        switch (notice.getType()) {
            case OneNotice.TYPE_MESSAGE:
                message();
                break;
            case OneNotice.TYPE_CONTACTS:
                contacts();
                break;
            case OneNotice.TYPE_COMMENT:
                if (notice.getContent().getTitle().equals("Проголосовали")) {
                    voice();
                } else if (notice.getContent().getTitle().equals("Ответ на комментарий")) {
                    answer();
                }
                break;

        }
    }

    public Notification.Builder result() {
        init();
        builder.setWhen(System.currentTimeMillis());
        return builder;
    }


}

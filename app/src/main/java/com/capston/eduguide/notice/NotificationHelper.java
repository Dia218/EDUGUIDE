package com.capston.eduguide.notice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

public class NotificationHelper {
    private Context context;

    public NotificationHelper(Context context){
        this.context=context;
    }
    public void createNotificationChannel(String channelId, String channelName,String channelDescription){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public NotificationManager getNotificationManager(){
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
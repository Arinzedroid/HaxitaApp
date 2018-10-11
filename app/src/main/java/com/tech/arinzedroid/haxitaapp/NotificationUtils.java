package com.tech.arinzedroid.haxitaapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.arinzedroid.tech.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String DESCRIPTION = "dummy notifictaion";


    public NotificationUtils(Context base) {
        super(base);
        //createChannels();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID, ANDROID_CHANNEL_NAME, importance);
            channel.setDescription(DESCRIPTION);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            getManager().createNotificationChannel(channel);
        }
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}

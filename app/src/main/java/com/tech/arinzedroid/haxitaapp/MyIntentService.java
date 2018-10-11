package com.tech.arinzedroid.haxitaapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;


public class MyIntentService extends Service {

    private String tag = "MyService";
    private Intent mIntent;
    private BroadcastReceiver myReceiver = new MyReceiver();

    public MyIntentService() {

    }


    @Override
    public void onCreate(){
        Log.e(tag,"Service onCreate");
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, NotificationUtils.ANDROID_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);

        registerReceiver(myReceiver,filter);

    }

    @Override
    public void onDestroy(){
        if(myReceiver != null)
            unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void openImage(){
        new CountDownTimer(600000, 1000) {
            @Override
            public void onTick(long l) {
                Log.e(tag,"time in secs = " + String.valueOf(l));
            }

            @Override
            public void onFinish() {
                if(mIntent != null ){
                    List<DataModel.Datum> datum = Parcels.unwrap(mIntent.getParcelableExtra("data"));
                    if(datum != null && !datum.isEmpty()){
                        Log.e(tag,"opening image");
                        Intent newIntent = new Intent(MyIntentService.this,ImageActivity.class);
                        newIntent.putExtra("data",Parcels.wrap(datum));
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyIntentService.this.startActivity(newIntent);
                    }else
                        Log.e(tag,"datum is null or empty");
                }else Log.e(tag,"mIntent is null");
            }
        }.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.e(tag,"onStartCommand");
        if(intent != null ) {
            mIntent = intent;
            List<DataModel.Datum> datum = Parcels.unwrap(intent.getParcelableExtra("data"));
            for(int i = 0; i < datum.size(); i++){
                Picasso.get().load(datum.get(i).getCampaignImages());
                Log.e(tag,"download image");
            }
        }
        return START_STICKY;
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(tag,"Receiver onReceive");
            if(intent.getAction() != null){
                Log.e(tag,intent.getAction());
                if(intent.getAction().equals(Intent.ACTION_SCREEN_ON) ){
                    Log.e(tag,"Screen is on");
                    openImage();
                }else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                    Log.e(tag,"Screen is off");
                }
            }
        }
    }
}

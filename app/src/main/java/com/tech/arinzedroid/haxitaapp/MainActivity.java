package com.tech.arinzedroid.haxitaapp;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    private int _requestCode = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();

    }

    private void checkPermissions(int code) {

        String[] permissions_required = new String[] {
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.INTERNET,
                };

        // check if permissions have been granted
        List<String> permissions_not_granted_list = new ArrayList<String>();
        for (String permission : permissions_required) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissions_not_granted_list.add(permission);
            }
        }
        // permissions not granted
        if (permissions_not_granted_list.size() > 0) {
            String[] permissions = new String[permissions_not_granted_list.size()];
            permissions_not_granted_list.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, code);
            Log.e("MainActivity", "permission requested");
        }
        else { // if all permissions have been granted
            startApp();
        }
    }

    private void startApp(){
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.text);

        NotificationUtils util = new NotificationUtils(this);

        downloadData();
    }

    private void downloadData(){
        final int visible = View.VISIBLE;
        progressBar.setVisibility(visible);
        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.getData().observe(this, data ->{
            if(data != null && data.getData() != null){

                final ImageView imageView = findViewById(R.id.image_view);
                for(int i = 0; i < data.getData().size(); i++){
                    Picasso.get().load(data.getData().get(i).getCampaignImages()).into(imageView);
                    Log.e("MainActivity","downloading  image");
                }

                Intent intent = new Intent(this,MyIntentService.class);
                intent.putExtra("data", Parcels.wrap(data.getData()));
                startService(intent);
                progressBar.setVisibility(View.GONE);
                textView.setText(String.valueOf("Data downloaded, service running"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // this is the answer to our permission request (our permission code)
        if (requestCode == _requestCode) {
            // check if all have been granted
            boolean ok = true;
            //  if(grantResults.length > 0)
            for (int grantResult : grantResults) {
                ok = ok && (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if(ok){
                //Pemmissions granted, proceed with the app
                startApp();
            } else {
                // exit if not all required permissions have been granted
                Toast.makeText(this, "Error: required permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}

package com.tech.arinzedroid.haxitaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;
import java.util.Random;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        ImageView imageView = findViewById(R.id.image_view);

        if(getIntent() != null){
            List<DataModel.Datum> data = Parcels.unwrap(getIntent().getParcelableExtra("data"));
            if(data != null){
                int i = new Random().nextInt(data.size() - 1);
                Picasso.get().load(data.get(i).getCampaignImages()).into(imageView);
            }
        }

    }
}

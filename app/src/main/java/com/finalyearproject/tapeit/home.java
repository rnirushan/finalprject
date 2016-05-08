package com.finalyearproject.tapeit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.finalyearproject.tapeit.R;

public class home extends AppCompatActivity {

    public ImageButton imgMeasureNow;
    public ImageButton imgShopNow;
    public Activity currentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        currentActivity = this;


        this.initGuiComponant();

        this.bindComponantEvents();
    }

    private void initGuiComponant() {
        imgMeasureNow = (ImageButton) findViewById(R.id.imgMeasureNow);
        imgShopNow = (ImageButton) findViewById(R.id.imgShopNow);
    }

    private void bindComponantEvents() {
        imgMeasureNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, Tapehome.class);
                currentActivity.startActivity(myIntent);
            }
        });

        imgShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, shopnow.class);
                currentActivity.startActivity(myIntent);
            }
        });
    }
}

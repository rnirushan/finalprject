package com.finalyearproject.tapeit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.finalyearproject.tapeit.R;

public class shopnow extends AppCompatActivity {
    private ImageButton imgCat1;
    public Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopnow);

        currentActivity = this;

        this.initGuiComponant();
        this.bindComponantEvents();
    }

    private void initGuiComponant() {
        imgCat1 = (ImageButton) findViewById(R.id.imgCat1);
    }

    private void bindComponantEvents() {
        imgCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                currentActivity.startActivity(myIntent);
            }
        });
    }
}

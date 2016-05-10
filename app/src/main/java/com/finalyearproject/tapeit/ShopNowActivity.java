package com.finalyearproject.tapeit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.finalyearproject.controllers.ShopController;
import com.finalyearproject.dto.Category;
import com.finalyearproject.tapeit.R;

import java.util.List;

public class ShopNowActivity extends AppCompatActivity {

    private ImageButton imgCat1;
    private ImageButton imgCat2;
    private ImageButton imgCat3;
    private ImageButton imgCat4;
    private ImageButton imgCat5;
    private ImageButton imgCat6;
    private ImageButton imgCat7;
    private ImageButton imgCat8;
    private ImageButton imgCat9;

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

        ShopController controller = new ShopController(this.getBaseContext());

        List<Category> categoryList = controller.getAllCategories();

        imgCat1 = (ImageButton) findViewById(R.id.imgCat1);
        imgCat1.setBackgroundResource(imgCat1.getContext().getResources().getIdentifier(
                categoryList.get(0).getIconPath(), "drawable", imgCat1.getContext().getPackageName()));

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

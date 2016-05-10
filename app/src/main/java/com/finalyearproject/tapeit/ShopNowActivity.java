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

        ShopController controller = new ShopController(this.getBaseContext());

        List<Category> categoryList = controller.getAllCategories();

        this.initGuiComponant(categoryList);
        this.bindComponantEvents(categoryList);
    }

    private void initGuiComponant(List<Category> categoryList) {



        imgCat1 = (ImageButton) findViewById(R.id.imgCat1);
        imgCat1.setBackgroundResource(imgCat1.getContext().getResources().getIdentifier(
                categoryList.get(0).getIconPath(), "drawable", imgCat1.getContext().getPackageName()));

        imgCat2 = (ImageButton) findViewById(R.id.imgCat2);
        imgCat2.setBackgroundResource(imgCat2.getContext().getResources().getIdentifier(
                categoryList.get(1).getIconPath(), "drawable", imgCat2.getContext().getPackageName()));

        imgCat3 = (ImageButton) findViewById(R.id.imgCat3);
        imgCat3.setBackgroundResource(imgCat3.getContext().getResources().getIdentifier(
                categoryList.get(2).getIconPath(), "drawable", imgCat3.getContext().getPackageName()));

        imgCat4 = (ImageButton) findViewById(R.id.imgCat4);
        imgCat4.setBackgroundResource(imgCat4.getContext().getResources().getIdentifier(
                categoryList.get(3).getIconPath(), "drawable", imgCat4.getContext().getPackageName()));

        imgCat5 = (ImageButton) findViewById(R.id.imgCat5);
        imgCat5.setBackgroundResource(imgCat5.getContext().getResources().getIdentifier(
                categoryList.get(4).getIconPath(), "drawable", imgCat5.getContext().getPackageName()));

        imgCat6 = (ImageButton) findViewById(R.id.imgCat6);
        imgCat6.setBackgroundResource(imgCat6.getContext().getResources().getIdentifier(
                categoryList.get(5).getIconPath(), "drawable", imgCat6.getContext().getPackageName()));

        imgCat7 = (ImageButton) findViewById(R.id.imgCat7);
        imgCat7.setBackgroundResource(imgCat7.getContext().getResources().getIdentifier(
                categoryList.get(6).getIconPath(), "drawable", imgCat7.getContext().getPackageName()));

        imgCat8 = (ImageButton) findViewById(R.id.imgCat8);
        imgCat8.setBackgroundResource(imgCat8.getContext().getResources().getIdentifier(
                categoryList.get(7).getIconPath(), "drawable", imgCat8.getContext().getPackageName()));

        imgCat9 = (ImageButton) findViewById(R.id.imgCat9);
        imgCat9.setBackgroundResource(imgCat9.getContext().getResources().getIdentifier(
                categoryList.get(8).getIconPath(), "drawable", imgCat9.getContext().getPackageName()));



    }

    private void bindComponantEvents(final List<Category> categoryList) {

        imgCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(0).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(1).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(2).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(3).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(4).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",String.valueOf(categoryList.get(5).getId()));
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",categoryList.get(6).getId());
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",categoryList.get(7).getId());
                currentActivity.startActivity(myIntent);
            }
        });
        imgCat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(currentActivity, SubCategoryActivity.class);
                myIntent.putExtra("categoryId",categoryList.get(8).getId());
                currentActivity.startActivity(myIntent);
            }
        });
    }
}

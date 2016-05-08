package com.finalyearproject.tapeit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubCategoryActivity extends AppCompatActivity {
    public Activity currentActivity;
    private ListView subCategoryLstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        currentActivity = this;

        this.initGuiComponant();

        this.bindComponantEvents();
    }

    private void initGuiComponant() {
        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X",
                "Android","IPhone","WindowsMobile","Blackberry"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);

        subCategoryLstView = (ListView) findViewById(R.id.lstViewSubCategory);
        subCategoryLstView.setAdapter(adapter);
    }

    private void bindComponantEvents() {
        subCategoryLstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(currentActivity, MeasureActivity.class);
                currentActivity.startActivity(myIntent);
            }
        });
    }
}

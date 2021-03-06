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

import com.finalyearproject.controllers.ShopController;
import com.finalyearproject.dto.Category;
import com.finalyearproject.dto.Measurement;
import com.finalyearproject.dto.SubCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {
    public Activity currentActivity;
    private ListView subCategoryLstView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        currentActivity = this;
        context =this;

        int categoryId = Integer.parseInt(getIntent().getStringExtra("categoryId"));
        ShopController controller = new ShopController(this);
        List<SubCategory> subCategoryList = controller.getSubCategories(categoryId);

        this.initGuiComponant(subCategoryList);

        this.bindComponantEvents();
    }

    private void initGuiComponant(List<SubCategory> subCategoryList) {

        String[] mobileArray = new String[subCategoryList.size()];
        int i = 0;

        for(SubCategory subCategory:subCategoryList){

            mobileArray[i] = subCategory.getTitle();
            i++;

        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mobileArray);

        subCategoryLstView = (ListView) findViewById(R.id.lstViewSubCategory);
        subCategoryLstView.setAdapter(adapter);
    }

    private void bindComponantEvents() {
        subCategoryLstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopController controller = new ShopController(context);
                String subCategoryName = (String) parent.getItemAtPosition(position);
                SubCategory subCategory = controller.getSubCategoryByName(subCategoryName);
                Category category = controller.getCategoryById(subCategory.getCategoryId());

                Intent myIntent = new Intent(currentActivity, MeasureActivity.class);
                myIntent.putExtra("title",subCategory.getTitle());
                myIntent.putExtra("category",category.getName());
                myIntent.putExtra("description",subCategory.getDescription());
                myIntent.putExtra("SUB_CAT_ID", subCategory.getId() + "");
                currentActivity.startActivity(myIntent);
            }
        });
    }
}

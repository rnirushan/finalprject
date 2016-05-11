package com.finalyearproject.tapeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalyearproject.controllers.ShopController;
import com.finalyearproject.dto.Measurement;
import com.finalyearproject.dto.SubCategory;
import com.finalyearproject.measurementadapter.MeasurementAdapter;

import java.util.ArrayList;

public class MeasureActivity extends AppCompatActivity {
    public ListView measurementLstView;
    public TextView txtMeasureDesTitle;
    public TextView txtMeasureTitle;
    public TextView txtMeasureCategory;
    public TextView txtMeasureDescription;
    int subCatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        txtMeasureDesTitle = (TextView) findViewById(R.id.txtMeasureDesTitle);
        txtMeasureTitle = (TextView)  findViewById(R.id.txtMeasureTitle);
        txtMeasureCategory = (TextView)  findViewById(R.id.txtMeasureCategory);
        txtMeasureDescription = (TextView) findViewById(R.id.txtMeasureDescription);


        String title = getIntent().getStringExtra("title");
        String category = getIntent().getStringExtra("category");
        String description = getIntent().getStringExtra("description");
        subCatId = Integer.parseInt(getIntent().getStringExtra("SUB_CAT_ID"));

        if(title != null){
            txtMeasureDesTitle.setText("Description for " + title);
            txtMeasureTitle.setText(title);
        }

        if(category != null){
            txtMeasureCategory.setText(category);
        }

        if(description != null){
            txtMeasureDescription.setText(description);
        }


        this.initGuiComponant();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGuiComponant();
    }

    private void initGuiComponant() {
        ShopController controller = new ShopController(this);
        ArrayList<Measurement> measurements = new ArrayList<Measurement>(controller.getMeasurements(subCatId));
        BaseAdapter adapter = new MeasurementAdapter(this, measurements);

        measurementLstView = (ListView) findViewById(R.id.lstViewMeasure);
        measurementLstView.setAdapter(adapter);
    }
}

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
        ArrayList<Measurement> measurements = getIntent().getParcelableArrayListExtra("measuement");

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


        this.initGuiComponant(measurements);
        this.bindComponantEvents();
    }

    private void initGuiComponant(ArrayList<Measurement> measurements) {

        String[] measurementArray = new String[measurements.size()];
        int i = 0;

        for(Measurement measurement:measurements){

            measurementArray[i] = measurement.getTitle();
            i++;

        }
        BaseAdapter adapter = new MeasurementAdapter(this, measurementArray);

        measurementLstView = (ListView) findViewById(R.id.lstViewMeasure);
        measurementLstView.setAdapter(adapter);
    }

    private void bindComponantEvents() {
        measurementLstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}

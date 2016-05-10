package com.finalyearproject.tapeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.finalyearproject.measurementadapter.MeasurementAdapter;

public class MeasureActivity extends AppCompatActivity {
    public ListView measurementLstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        this.initGuiComponant();
        this.bindComponantEvents();
    }

    private void initGuiComponant() {
        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X",
                "Android","IPhone","WindowsMobile","Blackberry"};

        BaseAdapter adapter = new MeasurementAdapter(this, mobileArray);

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

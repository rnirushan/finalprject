package com.finalyearproject.measurementadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyearproject.dto.Measurement;
import com.finalyearproject.tapeit.MeasureActivity;
import com.finalyearproject.tapeit.MeasurementActivity;
import com.finalyearproject.tapeit.R;
import com.finalyearproject.tapeit.ShopNowActivity;

import java.util.List;

public class MeasurementAdapter extends BaseAdapter {
    List<Measurement> measurements;
    Context context;
    private static LayoutInflater inflater=null;
    public MeasurementAdapter(Activity mainActivity, List<Measurement> measurements) {

        this.measurements = measurements;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return measurements.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView txtTitle;
        Button btnMeasurement;
        TextView txtMeasurement;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.measurement_listview, null);
        holder.txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        holder.btnMeasurement = (Button) rowView.findViewById(R.id.btnMeasurement);
        holder.txtMeasurement = (TextView) rowView.findViewById(R.id.txtMeasurement);

        holder.txtTitle.setText(measurements.get(position).getTitle());

        if(measurements.get(position).getValue() != null && measurements.get(position).getValue() != "" &&
                Double.parseDouble(measurements.get(position).getValue()) > 0.0){
            holder.txtMeasurement.setText("Measurement : " + measurements.get(position).getValue() + " cm");
        } else {
            holder.txtMeasurement.setText("-");
        }

        holder.btnMeasurement.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == 0) {
                            holder.btnMeasurement.setBackgroundColor(Color.parseColor("#9affde07"));
                        } else {
                            if (event.getAction() == 1) {
                                holder.btnMeasurement.setBackgroundColor(Color.parseColor("#ffde07"));
                                Intent myIntent = new Intent(context, MeasurementActivity.class);
                                myIntent.putExtra("MEASUREMENT", measurements.get(position));
                                context.startActivity(myIntent);
                            }
                        }
                        return true;
                    }
                }
        );
        return rowView;
    }
}

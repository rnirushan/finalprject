package com.finalyearproject.measurementadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyearproject.tapeit.MeasureActivity;
import com.finalyearproject.tapeit.MeasurementActivity;
import com.finalyearproject.tapeit.R;
import com.finalyearproject.tapeit.ShopNowActivity;

public class MeasurementAdapter extends BaseAdapter {
    String [] result;
    Context context;
    private static LayoutInflater inflater=null;
    public MeasurementAdapter(Activity mainActivity, String[] prgmNameList) {
        result=prgmNameList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.length;
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
        TextView tv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.measurement_listview, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.tv.setText(result[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(context, MeasurementActivity.class);
                context.startActivity(myIntent);
            }
        });
        return rowView;
    }
}

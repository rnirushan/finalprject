package com.finalyearproject.tapeit;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyearproject.logic.DistanceTracker;
import com.finalyearproject.logic.SingleData;
import com.finalyearproject.tapeit.R;

public class TapeHomeActivity extends AppCompatActivity implements SensorEventListener {
    public static DistanceTracker distanceTracker;
    private Sensor Acc;
    private static final int INCH = 1;
    private boolean logging;
    private static final int CM = 0;
    private int sem;
    private int set = 0;
    private float distance;
    public TextView txtMeasure;
    private String measure_errors;
    public Button btnMeasure;
    public TextView txtAlert;
    private SensorManager sm;

    static {
        distanceTracker = new DistanceTracker();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tapehome);

        this.initGuiComponant();
        this.bindComponantEvents();
        this.getSensors();

        this.distance = 0.0f;
    }

    private void getSensors() {
        this.sm = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        this.Acc = this.sm.getDefaultSensor(INCH);
        this.sm.registerListener(this, this.Acc, CM);
    }

    protected void onResume() {
        super.onResume();

        this.getSensors();
    }

    private void initGuiComponant() {
        this.btnMeasure = (Button) findViewById(R.id.btnMeasure);
        this.txtMeasure = (TextView) findViewById(R.id.txtMeasure);
        this.txtAlert = (TextView) findViewById(R.id.txtAlert);

        this.txtMeasure.setTextSize(2, 60.0f);
        this.txtMeasure.setText(distance + " cm");
    }

    private void bindComponantEvents() {
        btnMeasure.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == 0) {
                            btnMeasure.setBackgroundColor(Color.parseColor("#32ffde07"));
                            logging = false;
                            handleStartAndStop();
                        } else {
                            if (event.getAction() == INCH) {
                                btnMeasure.setBackgroundColor(Color.parseColor("#ffde07"));
                                logging = true;
                                handleStartAndStop();
                            }
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        if (this.logging && event.sensor.getType() == INCH) {
            distanceTracker.append(new SingleData(values[CM], values[INCH], values[2], event.timestamp));
        }
    }

    public void handleStartAndStop() {

        this.distance = 0.0f;
        if (this.logging) {
            float conv = 1.0f;
            this.logging = false;
            this.distance = distanceTracker.computeDistance();
            switch (this.set) {
                case CM /*0*/:
                    break;
                case INCH /*1*/:
                    conv = 0.39370078f;
                    break;
                default:
                    conv = 1.0f;
                    break;
            }
            analyzeErrors();
            accuracyAlert();
            if (this.sem != 3) {
                TextView textView;
                Object[] objArr;
                switch (this.set) {
                    case CM /*0*/:
                        textView = this.txtMeasure;
                        objArr = new Object[INCH];
                        objArr[CM] = Float.valueOf((this.distance * conv) * 100.0f);
                        textView.setText(String.format("%.1f cm", objArr));
                        return;
                    default:
                        return;
                }
            }
            return;
        }
        distanceTracker.clearAll();
        this.sem = CM;
        this.measure_errors = "";
        this.logging = true;

        this.txtMeasure.setText("--");
    }

    private void analyzeErrors() {
        this.sem = INCH;
        this.measure_errors = "";
        if (((distanceTracker.errByte >> INCH) & INCH) == INCH) {
            this.sem = 3;
            this.measure_errors = getString(R.string.lowspeed) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.lowspeed), Toast.LENGTH_LONG).show();
            //this.txtMeasure.setTextSize(2, 14.0f);

            //this.txtMeasure.setText(getString(R.string.howto));
        } else if (((distanceTracker.errByte >> 7) & INCH) == INCH) {
            this.sem = 3;
            this.measure_errors = getString(R.string.starttooearly) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.starttooearly), Toast.LENGTH_LONG).show();
            //this.txtMeasure.setTextSize(2, 14.0f);

            //this.txtMeasure.setText(getString(R.string.howto));
        } else if (((distanceTracker.errByte >> 6) & INCH) == INCH) {
            this.sem = 3;
            this.measure_errors = getString(R.string.stoptooearly) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.stoptooearly), Toast.LENGTH_LONG).show();
            //this.txtMeasure.setTextSize(2, 14.0f);

            //this.txtMeasure.setText(getString(R.string.howto));
        } else if (((distanceTracker.errByte >> 2) & INCH) == INCH) {
            this.sem = 2;
            this.measure_errors = getString(R.string.toofastorvertical) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.toofastorvertical), Toast.LENGTH_LONG).show();
        } else if (((distanceTracker.errByte >> 4) & INCH) == INCH) {
            this.distance = 0.0f;
            this.sem = 3;
            this.measure_errors = getString(R.string.highrotation) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.highrotation), Toast.LENGTH_LONG).show();
            //this.txtMeasure.setTextSize(2, 14.0f);

            //this.txtMeasure.setText(getString(R.string.howto));
        } else if (((distanceTracker.errByte >> 3) & INCH) == INCH) {
            this.sem = 2;
            this.measure_errors = getString(R.string.highrotation) + "\n\n" + this.measure_errors;
            Toast.makeText(getApplicationContext(), getString(R.string.highrotation), Toast.LENGTH_LONG).show();
        } else if (this.sem == INCH) {
            this.measure_errors = getString(R.string.green);
        }
    }

    public void accuracyAlert() {
        switch (this.sem) {
            case 0:
                break;
            case 1:
                this.txtAlert.setTextColor(Color.parseColor("#07ff24"));
                this.txtAlert.setText(getString(R.string.highaccuracy));
                break;
            case 2:
                this.txtAlert.setTextColor(Color.parseColor("#FFFF9807"));
                this.txtAlert.setText(getString(R.string.mediumaccuracy));
                break;
            case 3:
                this.txtAlert.setTextColor(Color.parseColor("#FFFF070B"));
                this.txtAlert.setText(getString(R.string.lowaccuracy));
                break;
            default:
                break;
        }
    }
}

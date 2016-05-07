package com.finalyearproject.logic;

import android.util.Log;

import java.util.ArrayList;

public class DistanceTracker {
    private float a_min;
    private float ax0;
    private float ax1;
    private float ay0;
    private float ay1;
    private float az0;
    private float az1;
    public ArrayList<SingleData> data;
    public ArrayList<SingleData> dataOk;
    public byte errByte;
    private float glim;
    private int k2;
    double max_axis;
    double max_axis_low;
    double max_other_axes;
    double max_other_axes_low;
    private boolean measure_ok;
    private int n_var;
    public String nail;
    private int start;
    private int stop;
    private float sum_x;
    private float sum_y;
    private float sum_z;
    private float tstart;
    private float tstop;
    private float var_lim;
    private float var_mult;
    private float var_mult2;
    private int w;

    public DistanceTracker() {
        this.nail = "chiodo";
        this.data = new ArrayList();
        this.dataOk = new ArrayList();
        this.var_lim = 0.0f;
        this.w = 5;
        this.var_mult = 2.0f;
        this.var_mult2 = 5.0f;
        this.glim = 12.0f;
        this.errByte = (byte) 0;
        this.start = 0;
        this.stop = 0;
        this.k2 = 0;
        this.sum_x = 0.0f;
        this.sum_y = 0.0f;
        this.sum_z = 0.0f;
        this.tstart = 0.0f;
        this.tstop = 0.0f;
        this.ax0 = 0.0f;
        this.ay0 = 0.0f;
        this.az0 = 0.0f;
        this.ax1 = 0.0f;
        this.ay1 = 0.0f;
        this.az1 = 0.0f;
        this.a_min = 12.0f;
        this.max_axis = 0.45d;
        this.max_axis_low = 0.2d;
        this.max_other_axes = 0.55d;
        this.max_other_axes_low = 0.25d;
        this.measure_ok = true;
    }

    public float computeDistance() {
        if (((float) this.data.size()) > (this.var_mult * ((float) this.w)) + 1.0f) {
            SingleData singleData;
            int ind_maxg1;
            this.errByte = (byte) 0;
            this.measure_ok = true;
            timeInit();
            int k = this.w - 1;
            while (true) {
                if (k >= this.data.size()) {
                    break;
                }
                float[] var = new float[4];
                var = var(k);
                singleData = (SingleData) this.data.get(k);
                singleData.varx = var[0];
                singleData = (SingleData) this.data.get(k);
                singleData.vary = var[1];
                singleData = (SingleData) this.data.get(k);
                singleData.varz = var[2];
                singleData = (SingleData) this.data.get(k);
                singleData.varg = var[3];
                k++;
            }
            this.var_lim = computeVarLim();
            startANDstop();
            removeFakeSTST();
            onlyOneSTST();
            int u = 0;
            while (true) {
                if (u >= this.data.size()) {
                    break;
                }
                if (((SingleData) this.data.get(u)).stst_t) {
                    this.dataOk.add((SingleData) this.data.get(u));
                }
                u++;
            }

            if (this.dataOk.size() == 0) {
                this.errByte = (byte) (this.errByte | 32);
                this.dataOk.clear();
                this.var_lim = 0.2f;
                startANDstop();
                removeFakeSTST();
                onlyOneSTST();
                u = 0;
                while (true) {
                    if (u >= this.data.size()) {
                        break;
                    }
                    if (((SingleData) this.data.get(u)).stst_t) {
                        this.dataOk.add((SingleData) this.data.get(u));
                    }
                    u++;
                }
            }
            int i = 0;
            while (true) {
                if (i >= this.dataOk.size()) {
                    break;
                }
                singleData = (SingleData) this.dataOk.get(i);
                singleData.ax = (((SingleData) this.dataOk.get(i)).ax - this.ax0) - (((((SingleData) this.dataOk.get(i)).timeSec - this.tstart) * (this.ax1 - this.ax0)) / (this.tstop - this.tstart));
                singleData = (SingleData) this.dataOk.get(i);
                singleData.ay = (((SingleData) this.dataOk.get(i)).ay - this.ay0) - (((((SingleData) this.dataOk.get(i)).timeSec - this.tstart) * (this.ay1 - this.ay0)) / (this.tstop - this.tstart));
                singleData = (SingleData) this.dataOk.get(i);
                singleData.az = (((SingleData) this.dataOk.get(i)).az - this.az0) - (((((SingleData) this.dataOk.get(i)).timeSec - this.tstart) * (this.az1 - this.az0)) / (this.tstop - this.tstart));
                i++;
            }
            cumtrapzV();
            float meanVx = 0.0f;
            float meanVy = 0.0f;
            float meanVz = 0.0f;
            float maxVx = 0.0f;
            float maxVy = 0.0f;
            float maxVz = 0.0f;
            int end = this.dataOk.size();
            for (i = 0; i < end; i++) {
                meanVx += ((SingleData) this.dataOk.get(i)).vx;
                meanVy += ((SingleData) this.dataOk.get(i)).vy;
                meanVz += ((SingleData) this.dataOk.get(i)).vz;
                if (Math.abs(((SingleData) this.dataOk.get(i)).vx) > maxVx) {
                    maxVx = Math.abs(((SingleData) this.dataOk.get(i)).vx);
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).vy) > maxVy) {
                    maxVy = Math.abs(((SingleData) this.dataOk.get(i)).vy);
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).vz) > maxVz) {
                    maxVz = Math.abs(((SingleData) this.dataOk.get(i)).vz);
                }
            }
            meanVx /= (float) end;
            meanVy /= (float) end;
            meanVz /= (float) end;
            float maxV = Math.max(Math.abs(maxVx), Math.max(Math.abs(maxVy), Math.abs(maxVz)));
            errorRotation(maxVx, maxVy, maxVz, maxV);
            for (i = 0; i < end; i++) {
                singleData = (SingleData) this.dataOk.get(i);
                float f = ((SingleData) this.dataOk.get(i)).vx;
                float f2 = (((SingleData) this.dataOk.get(i)).timeSec - this.tstart) / (this.tstop - this.tstart);
                singleData.vx = f - (((SingleData) this.dataOk.get(end - 1)).vx * f2);
                singleData = (SingleData) this.dataOk.get(i);
                f = ((SingleData) this.dataOk.get(i)).vy;
                f2 = (((SingleData) this.dataOk.get(i)).timeSec - this.tstart) / (this.tstop - this.tstart);
                singleData.vy = f - (((SingleData) this.dataOk.get(end - 1)).vy * f2);
                singleData = (SingleData) this.dataOk.get(i);
                f = ((SingleData) this.dataOk.get(i)).vz;
                f2 = (((SingleData) this.dataOk.get(i)).timeSec - this.tstart) / (this.tstop - this.tstart);
                singleData.vz = f - (((SingleData) this.dataOk.get(end - 1)).vz * f2);
            }
            cumtrapzS();
            float axmax = 0.0f;
            float axmin = Float.MAX_VALUE;
            float aymax = 0.0f;
            float aymin = Float.MAX_VALUE;
            float azmax = 0.0f;
            float azmin = Float.MAX_VALUE;
            float agmax = 0.0f;
            int axmaxi = 0;
            int aymaxi = 0;
            int azmaxi = 0;
            for (i = 0; i < end; i++) {
                if (Math.abs(((SingleData) this.dataOk.get(i)).ax) > axmax) {
                    axmax = Math.abs(((SingleData) this.dataOk.get(i)).ax);
                    axmaxi = i;
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).ay) > aymax) {
                    aymax = Math.abs(((SingleData) this.dataOk.get(i)).ay);
                    aymaxi = i;
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).az) > azmax) {
                    azmax = Math.abs(((SingleData) this.dataOk.get(i)).az);
                    azmaxi = i;
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).g) > agmax) {
                    agmax = Math.abs(((SingleData) this.dataOk.get(i)).g);
                    int agmaxi = i;
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).ax) < axmin) {
                    axmin = Math.abs(((SingleData) this.dataOk.get(i)).ax);
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).ay) < aymin) {
                    aymin = Math.abs(((SingleData) this.dataOk.get(i)).ay);
                }
                if (Math.abs(((SingleData) this.dataOk.get(i)).az) < azmin) {
                    azmin = Math.abs(((SingleData) this.dataOk.get(i)).az);
                }
                if (checkSaturation(i)) {
                    this.errByte = (byte) (this.errByte | 4);
                }
            }
            if (maxV == maxVx) {
                ind_maxg1 = axmaxi;
            }
            if (maxV == maxVy) {
                ind_maxg1 = aymaxi;
            }
            if (maxV == maxVz) {
                ind_maxg1 = azmaxi;
            }
            if (agmax < this.a_min) {
                this.errByte = (byte) (this.errByte | 2);
                this.measure_ok = false;
            }
            if (!this.measure_ok) {
                return 0.0f;
            }
            return ((float) Math.round(1000.0d * Math.sqrt((Math.pow((double) ((SingleData) this.dataOk.get(this.dataOk.size() - 1)).sx, 2.0d) + Math.pow((double) ((SingleData) this.dataOk.get(this.dataOk.size() - 1)).sy, 2.0d)) + Math.pow((double) ((SingleData) this.dataOk.get(this.dataOk.size() - 1)).sz, 2.0d)))) / 1000.0f;
        }
        this.errByte = (byte) (this.errByte | 1);
        return 0.0f;
    }

    private void errorRotation(float maxVx, float maxVy, float maxVz, float maxV) {
        int end = this.dataOk.size();
        if (false) {
            if (maxV == maxVx && end != 0) {
                Log.d("acc", "vAXES/VMAX = " + Math.abs(((SingleData) this.dataOk.get(end - 1)).vx / maxV));
                Log.d("acc", "vOTHER/VMAX = " + Math.max(Math.abs(((SingleData) this.dataOk.get(end - 1)).vy / maxV), Math.abs(((SingleData) this.dataOk.get(end - 1)).vz / maxV)));
            }
            if (maxV == maxVy && end != 0) {
                Log.d("acc", "vAXES/VMAX = " + Math.abs(((SingleData) this.dataOk.get(end - 1)).vy / maxV));
                Log.d("acc", "vOTHER/VMAX = " + Math.max(Math.abs(((SingleData) this.dataOk.get(end - 1)).vx / maxV), Math.abs(((SingleData) this.dataOk.get(end - 1)).vz / maxV)));
            }
            if (maxV == maxVz && end != 0) {
                Log.d("acc", "vAXES/VMAX = " + Math.abs(((SingleData) this.dataOk.get(end - 1)).vz / maxV));
                Log.d("acc", "vOTHER/VMAX = " + Math.max(Math.abs(((SingleData) this.dataOk.get(end - 1)).vx / maxV), Math.abs(((SingleData) this.dataOk.get(end - 1)).vy / maxV)));
                return;
            }
            return;
        }
        if (maxV == maxVx && end != 0) {
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) >= this.max_axis * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) >= this.max_axis_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) > this.max_other_axes * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) > this.max_other_axes * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) > this.max_other_axes_low * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) > this.max_other_axes_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
        }
        if (maxV == maxVy && end != 0) {
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) >= this.max_axis * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) >= this.max_axis_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) > this.max_other_axes * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) > this.max_other_axes * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) > this.max_other_axes_low * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) > this.max_other_axes_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
        }
        if (maxV == maxVz && end != 0) {
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) >= this.max_axis * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vz)) >= this.max_axis_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) > this.max_other_axes * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) > this.max_other_axes * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 16);
                this.measure_ok = false;
            }
            if (((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vx)) > this.max_other_axes_low * ((double) maxV) || ((double) Math.abs(((SingleData) this.dataOk.get(end - 1)).vy)) > this.max_other_axes_low * ((double) maxV)) {
                this.errByte = (byte) (this.errByte | 8);
            }
        }
    }

    private void onlyOneSTST() {
        int i;
        this.start = 0;
        this.stop = 0;
        this.k2 = 0;
        this.sum_x = 0.0f;
        this.sum_y = 0.0f;
        this.sum_z = 0.0f;
        this.tstart = 0.0f;
        this.tstop = 0.0f;
        this.ax0 = 0.0f;
        this.ay0 = 0.0f;
        this.az0 = 0.0f;
        int k = 0;
        while (k < this.data.size()) {
            if (this.start == 0 && !((SingleData) this.data.get(k)).stst_t) {
                this.sum_x = ((SingleData) this.data.get(k)).ax + this.sum_x;
                this.sum_y = ((SingleData) this.data.get(k)).ay + this.sum_y;
                this.sum_z = ((SingleData) this.data.get(k)).az + this.sum_z;
            }
            if (this.start == 0 && ((SingleData) this.data.get(k)).stst_t) {
                this.start = 1;
                this.tstart = ((SingleData) this.data.get(k)).timeSec;
                if (k == 0) {
                    k = 1;
                }
                this.ax0 = this.sum_x / ((float) k);
                this.ay0 = this.sum_y / ((float) k);
                this.az0 = this.sum_z / ((float) k);
                this.sum_x = 0.0f;
                this.sum_y = 0.0f;
                this.sum_z = 0.0f;
            }
            if (this.start == 1 && this.stop == 0 && !((SingleData) this.data.get(k)).stst_t) {
                this.stop = 1;
                this.tstop = ((SingleData) this.data.get(k)).timeSec;
            }
            if (this.start == 1 && this.stop == 1 && !((SingleData) this.data.get(k)).stst_t) {
                this.sum_x = ((SingleData) this.data.get(k)).ax + this.sum_x;
                this.sum_y = ((SingleData) this.data.get(k)).ay + this.sum_y;
                this.sum_z = ((SingleData) this.data.get(k)).az + this.sum_z;
                this.k2++;
            }
            if (this.start == 1 && this.stop == 1 && ((SingleData) this.data.get(k)).stst_t) {
                for (int y = k; y < this.data.size(); y++) {
                    ((SingleData) this.data.get(y)).stst_t = false;
                }
                if (this.k2 == 0) {
                    this.k2 = 1;
                }
                this.ax1 = this.sum_x / ((float) this.k2);
                this.ay1 = this.sum_y / ((float) this.k2);
                this.az1 = this.sum_z / ((float) this.k2);
                if (((SingleData) this.data.get(this.data.size() - 1)).stst_t) {
                    this.errByte = (byte) (this.errByte | 64);
                    this.ax1 = 0.0f;
                    this.ay1 = 0.0f;
                    this.az1 = 0.0f;
                    this.tstop = ((SingleData) this.data.get(this.data.size() - 5)).timeSec;
                    for (i = 1; i <= 5; i++) {
                        ((SingleData) this.data.get(this.data.size() - i)).stst_t = false;
                        this.ax1 = ((SingleData) this.data.get(this.data.size() - i)).ax + this.ax1;
                        this.ay1 = ((SingleData) this.data.get(this.data.size() - i)).ay + this.ay1;
                        this.az1 = ((SingleData) this.data.get(this.data.size() - i)).az + this.az1;
                    }
                    this.ax1 /= (float) 5;
                    this.ay1 /= (float) 5;
                    this.az1 /= (float) 5;
                }
            }
            k++;
        }
        if (this.k2 == 0) {
            this.k2 = 1;
        }
        this.ax1 = this.sum_x / ((float) this.k2);
        this.ay1 = this.sum_y / ((float) this.k2);
        this.az1 = this.sum_z / ((float) this.k2);
        if (((SingleData) this.data.get(this.data.size() - 1)).stst_t) {
            this.errByte = (byte) (this.errByte | 64);
            this.ax1 = 0.0f;
            this.ay1 = 0.0f;
            this.az1 = 0.0f;
            this.tstop = ((SingleData) this.data.get(this.data.size() - 5)).timeSec;
            for (i = 1; i <= 5; i++) {
                ((SingleData) this.data.get(this.data.size() - i)).stst_t = false;
                this.ax1 = ((SingleData) this.data.get(this.data.size() - i)).ax + this.ax1;
                this.ay1 = ((SingleData) this.data.get(this.data.size() - i)).ay + this.ay1;
                this.az1 = ((SingleData) this.data.get(this.data.size() - i)).az + this.az1;
            }
            this.ax1 /= (float) 5;
            this.ay1 /= (float) 5;
            this.az1 /= (float) 5;
        }
    }

    private void removeFakeSTST() {
        int cont = 0;
        boolean fake = true;
        for (int i = 0; i < this.data.size(); i++) {
            if (cont != 0) {
                if (!((SingleData) this.data.get(i)).stst_t) {
                    if (fake) {
                        for (int ii = i - cont; ii <= i - 1; ii++) {
                            ((SingleData) this.data.get(ii)).stst_t = false;
                        }
                    }
                    cont = 0;
                    fake = true;
                } else if (((SingleData) this.data.get(i)).varg > this.var_lim * this.var_mult2) {
                    fake = false;
                }
                cont++;
            } else if (((SingleData) this.data.get(i)).stst_t) {
                cont++;
            }
            ((SingleData) this.data.get(i)).fake = fake;
        }
    }

    private void startANDstop() {
        int startg = 0;
        int endg = 0;
        int i = 0;
        while (i < this.data.size()) {
            if (((SingleData) this.data.get(i)).g > this.glim - 1.0f && startg == 0) {
                startg = i;
            }
            if (((SingleData) this.data.get(i)).g > this.glim - 1.0f && i > startg && startg > 0) {
                endg = i;
            }
            i++;
        }
        if (startg < this.w) {
            this.errByte = (byte) (this.errByte | 128);
        }
        for (i = 0; i < this.w - 1; i++) {
            ((SingleData) this.data.get(i)).stst_t = false;
        }
        i = this.w - 1;
        while (i < this.data.size()) {
            float var_temp = 0.0f;
            for (int ii = (i - this.w) + 1; ii <= i; ii++) {
                var_temp += ((SingleData) this.data.get(ii)).varg;
            }
            if (i < startg || i > endg) {
                ((SingleData) this.data.get(i)).stst_t = false;
            } else {
                ((SingleData) this.data.get(i)).stst_t = true;
            }
            if (var_temp > this.var_lim) {
                ((SingleData) this.data.get(i)).stst_t = true;
            }
            i++;
        }
        for (i = 0; i < this.data.size() - (this.w - 1); i++) {
            ((SingleData) this.data.get(i)).stst_t2 = ((SingleData) this.data.get((this.w - 1) + i)).stst_t;
        }
        ((SingleData) this.data.get(this.data.size() - 1)).stst_t2 = false;
        ((SingleData) this.data.get(this.data.size() - 2)).stst_t2 = false;
        ((SingleData) this.data.get(this.data.size() - 3)).stst_t2 = false;
        ((SingleData) this.data.get(this.data.size() - 4)).stst_t2 = false;
        for (i = 0; i < this.data.size() - (this.w - 1); i++) {
            ((SingleData) this.data.get(i)).stst_t = ((SingleData) this.data.get(i)).stst_t2 & ((SingleData) this.data.get(i)).stst_t;
        }
        for (i = 0; i < this.data.size() - this.w; i++) {
            ((SingleData) this.data.get(i)).stst_t3 = ((SingleData) this.data.get(this.w + i)).stst_t;
        }
        ((SingleData) this.data.get(this.data.size() - 1)).stst_t3 = false;
        ((SingleData) this.data.get(this.data.size() - 2)).stst_t3 = false;
        ((SingleData) this.data.get(this.data.size() - 3)).stst_t3 = false;
        ((SingleData) this.data.get(this.data.size() - 4)).stst_t3 = false;
        ((SingleData) this.data.get(this.data.size() - 5)).stst_t3 = false;
        for (i = 0; i < this.data.size() - this.w; i++) {
            ((SingleData) this.data.get(i)).stst_t = ((SingleData) this.data.get(i)).stst_t3 | ((SingleData) this.data.get(i)).stst_t;
        }
    }

    private float computeVarLim() {
        int ii;
        float var_temp2;
        this.var_lim = Float.MAX_VALUE;
        this.n_var = (int) (((float) this.data.size()) - Math.ceil(this.var_mult * ((float) this.w)));
        float[] varArray = new float[this.n_var];
        for (ii = 0; ii < this.n_var; ii++) {
            var_temp2 = 0.0f;
            for (int i = ii; ((float) i) < Math.floor(((float) ii) + (this.var_mult * ((float) this.w))); i++) {
                var_temp2 += ((SingleData) this.data.get(i)).varg;
            }
            varArray[ii] = var_temp2;
            if (var_temp2 < this.var_lim) {
                this.var_lim = var_temp2;
            }
        }
        var_temp2 = Float.MAX_VALUE;
        ii = 0;
        while (ii < this.n_var) {
            if (varArray[ii] != this.var_lim && varArray[ii] < var_temp2) {
                var_temp2 = varArray[ii];
            }
            ii++;
        }
        this.var_lim = var_temp2;
        this.var_lim *= 10.0f;
        return this.var_lim;
    }

    private void timeInit() {
        long firstT = ((SingleData) this.data.get(0)).timeNano;
        int i = 0;
        while (i < this.data.size()) {
            ((SingleData) this.data.get(i)).timeNano -= firstT;
            ((SingleData) this.data.get(i)).timeSec = ((float) ((SingleData) this.data.get(i)).timeNano) / 1.0E9f;
            i++;
        }
    }

    private void cumtrapzV() {
        float cumx = 0.0f;
        float cumy = 0.0f;
        float cumz = 0.0f;
        for (int i = 1; i < this.dataOk.size(); i++) {
            cumx += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).ax + ((SingleData) this.dataOk.get(i)).ax) / 2.0f);
            cumy += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).ay + ((SingleData) this.dataOk.get(i)).ay) / 2.0f);
            cumz += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).az + ((SingleData) this.dataOk.get(i)).az) / 2.0f);
            ((SingleData) this.dataOk.get(i)).vx = cumx;
            ((SingleData) this.dataOk.get(i)).vy = cumy;
            ((SingleData) this.dataOk.get(i)).vz = cumz;
        }
    }

    private void cumtrapzS() {
        float cumx = 0.0f;
        float cumy = 0.0f;
        float cumz = 0.0f;
        for (int i = 1; i < this.dataOk.size(); i++) {
            cumx += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).vx + ((SingleData) this.dataOk.get(i)).vx) / 2.0f);
            cumy += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).vy + ((SingleData) this.dataOk.get(i)).vy) / 2.0f);
            cumz += (((SingleData) this.dataOk.get(i)).timeSec - ((SingleData) this.dataOk.get(i - 1)).timeSec) * ((((SingleData) this.dataOk.get(i - 1)).vz + ((SingleData) this.dataOk.get(i)).vz) / 2.0f);
            ((SingleData) this.dataOk.get(i)).sx = cumx;
            ((SingleData) this.dataOk.get(i)).sy = cumy;
            ((SingleData) this.dataOk.get(i)).sz = cumz;
            ((SingleData) this.dataOk.get(i)).s = (float) Math.sqrt((Math.pow((double) cumx, 2.0d) + Math.pow((double) cumy, 2.0d)) + Math.pow((double) cumz, 2.0d));
        }
    }

    private float[] var(int k) {
        int i;
        float[] m = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        float[] v = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        for (i = (k - this.w) + 1; i <= k; i++) {
            m[0] = ((SingleData) this.data.get(i)).ax + m[0];
            m[1] = ((SingleData) this.data.get(i)).ay + m[1];
            m[2] = ((SingleData) this.data.get(i)).az + m[2];
            m[3] = ((SingleData) this.data.get(i)).g + m[3];
        }
        m[0] = m[0] / ((float) this.w);
        m[1] = m[1] / ((float) this.w);
        m[2] = m[2] / ((float) this.w);
        m[3] = m[3] / ((float) this.w);
        for (i = (k - this.w) + 1; i <= k; i++) {
            v[0] = ((float) Math.pow((double) (((SingleData) this.data.get(i)).ax - m[0]), 2.0d)) + v[0];
            v[1] = ((float) Math.pow((double) (((SingleData) this.data.get(i)).ay - m[1]), 2.0d)) + v[1];
            v[2] = ((float) Math.pow((double) (((SingleData) this.data.get(i)).az - m[2]), 2.0d)) + v[2];
            v[3] = ((float) Math.pow((double) (((SingleData) this.data.get(i)).g - m[3]), 2.0d)) + v[3];
        }
        v[0] = v[0] / ((float) (this.w - 1));
        v[1] = v[1] / ((float) (this.w - 1));
        v[2] = v[2] / ((float) (this.w - 1));
        v[3] = v[3] / ((float) (this.w - 1));
        return v;
    }

    private boolean checkSaturation(int k) {
        if (k > this.dataOk.size() - 4) {
            return false;
        }
        if (Math.abs(((SingleData) this.dataOk.get(k)).ax) > 15.0f && Math.abs(((SingleData) this.dataOk.get(k + 1)).ax) > 15.0f && Math.abs(((SingleData) this.dataOk.get(k + 2)).ax) > 15.0f && ((double) Math.abs(((SingleData) this.dataOk.get(k)).ax - ((SingleData) this.dataOk.get(k + 1)).ax)) < 0.01d && ((double) Math.abs(((SingleData) this.dataOk.get(k + 1)).ax - ((SingleData) this.dataOk.get(k + 2)).ax)) < 0.01d) {
            return true;
        }
        if (Math.abs(((SingleData) this.dataOk.get(k)).ay) > 15.0f && Math.abs(((SingleData) this.dataOk.get(k + 1)).ay) > 15.0f && Math.abs(((SingleData) this.dataOk.get(k + 2)).ay) > 15.0f && ((double) Math.abs(((SingleData) this.dataOk.get(k)).ay - ((SingleData) this.dataOk.get(k + 1)).ay)) < 0.01d && ((double) Math.abs(((SingleData) this.dataOk.get(k + 1)).ay - ((SingleData) this.dataOk.get(k + 2)).ay)) < 0.01d) {
            return true;
        }
        if (Math.abs(((SingleData) this.dataOk.get(k)).az) <= 15.0f || Math.abs(((SingleData) this.dataOk.get(k + 1)).az) <= 15.0f || Math.abs(((SingleData) this.dataOk.get(k + 2)).az) <= 15.0f || ((double) Math.abs(((SingleData) this.dataOk.get(k)).az - ((SingleData) this.dataOk.get(k + 1)).az)) >= 0.01d || ((double) Math.abs(((SingleData) this.dataOk.get(k + 1)).az - ((SingleData) this.dataOk.get(k + 2)).az)) >= 0.01d) {
            return false;
        }
        return true;
    }

    public float getDistance() {
        if (this.data.size() > 0) {
            return ((SingleData) this.data.get(this.data.size() - 1)).s;
        }
        return 0.0f;
    }

    public void append(SingleData o) {
        this.data.add(o);
    }

    public SingleData get(int i) {
        return (SingleData) this.data.get(i);
    }

    public void clearAll() {
        this.data.clear();
        this.dataOk.clear();
    }
}

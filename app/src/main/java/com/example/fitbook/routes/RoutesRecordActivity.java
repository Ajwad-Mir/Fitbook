package com.example.fitbook.routes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitbook.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class RoutesRecordActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private static final String TAG = "RoutesRecordActivity";
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private double lastX = 0, lastY = 0, lastZ = 0;
    private int steps = 0, counter = 0, oldSteps = 0;

    private double height = 0.0;

    private long startTime = 0, startWatch = 0, stopWatch = 0;
    private int walked = 0;
    private double meters = 0, runningSpeed = 0;

    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private ArrayList<Double> z;

    private ArrayList<Double> dataX;
    private ArrayList<Double> dataY;
    private ArrayList<Double> dataZ;

    private TextView stepsNumber, distance, speed;
    private Chronometer watch;
    private Button start, stop;
    private EditText heightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_record);

        initializeViews();

        startTime = System.currentTimeMillis();

        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        dataX = new ArrayList<>();
        dataY = new ArrayList<>();
        dataZ = new ArrayList<>();

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        stepsNumber.setEnabled(false);
        distance.setEnabled(false);
        speed.setEnabled(false);
        stop.setEnabled(false);
        start.setEnabled(true);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }


    }

    public void initializeViews() {
        stepsNumber = findViewById(R.id.stepsNumber);
        distance = findViewById(R.id.distance);
        speed = findViewById(R.id.speed);
        watch = findViewById(R.id.chronometer);
        heightData = findViewById(R.id.heightData);
        start = findViewById(R.id.start_button);
        stop = findViewById(R.id.stop_button);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(start)) {

            if(TextUtils.isEmpty(heightData.getText().toString()))
            {
                heightData.setError("Enter Height!!!");
                heightData.requestFocus();
            }
            else
            {

                watch.setBase(SystemClock.elapsedRealtime() + stopWatch);
                watch.start();

                startWatch = System.currentTimeMillis();

                height = Double.parseDouble(heightData.getText().toString());
                stepsNumber.setEnabled(true);
                distance.setEnabled(true);
                speed.setEnabled(true);
                stop.setEnabled(true);
                start.setEnabled(false);
                heightData.setEnabled(false);

                resetValues();

                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            }
        } else if (v.equals(stop)) {

            stepsNumber.setEnabled(false);
            distance.setEnabled(false);
            speed.setEnabled(false);
            stop.setEnabled(false);
            start.setEnabled(true);
            heightData.setEnabled(true);

            stopWatch = SystemClock.elapsedRealtime() - watch.getBase();
            watch.stop();

            String Time = watch.getText().toString();
            Log.d(TAG,"time: " + stopWatch);
            sensorManager.unregisterListener(this);
            String RouteID = getIntent().getStringExtra("RouteID");
            String RouteName = getIntent().getStringExtra("RouteName");
            String RouteShoe = getIntent().getStringExtra("RouteShoe");
            Intent intent = new Intent(RoutesRecordActivity.this, RoutesFinishActivity.class);
            intent.putExtra("RouteID",RouteID);
            intent.putExtra("RouteName",RouteName);
            intent.putExtra("RouteShoe",RouteShoe);
            intent.putExtra("DistanceTravelled",meters);
            intent.putExtra("StepsTaken",steps);
            intent.putExtra("TimeElapsed",Time);
            startActivity(intent);
        }
    }

    public void resetValues() {

        watch.setBase(SystemClock.elapsedRealtime());
        meters = 0;
        steps = 0;
        runningSpeed = 0;

        distance.setText("0");
        stepsNumber.setText("0");
        speed.setText("0");
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double deltaX = Math.abs(lastX - event.values[0]);
        double deltaY = Math.abs(lastY - event.values[1]);
        double deltaZ = Math.abs(lastZ - event.values[2]);


        // if the change is below 2, it is just noise
        double precision = 2;
        if (deltaX > precision) {
            x.add((double) event.values[0]);
            lastX = event.values[0];
        } else {
            x.add(lastX);
        }
        if (deltaY > precision) {
            y.add((double) event.values[1]);
            lastY = event.values[1];
        } else {
            y.add(lastY);
        }
        if (deltaZ > precision) {
            z.add((double) event.values[2]);
            lastZ = event.values[2];
        } else {
            z.add(lastZ);
        }

        counter++;

        if (counter == 200) {
            if (System.currentTimeMillis() > startTime + 200) {
                walked = steps - oldSteps;
                startTime = System.currentTimeMillis();
                oldSteps = steps;

            }
            updateSteps();
            calculateData();
            counter = 0;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("SetTextI18n")
    public void calculateData() {

        // distance
        if (walked == 1) {
            meters += height / 5.0;
        } else if (walked == 2) {
            meters += height / 4.0;
        } else if (walked == 3) {
            meters += height / 3.0;
        } else if (walked == 4) {
            meters += height / 2.0;
        } else if (walked == 5) {
            meters += height / 1.2;
        } else if (walked >= 6 && walked < 8) {
            meters += height;
        } else if (walked >= 8) {
            meters += height * 1.2;
        }

        // update speed
        runningSpeed = meters / (((System.currentTimeMillis() - startWatch) * 1000));
        speed.setText(Double.toString(runningSpeed));

        // update distance
        distance.setText(Double.toString(meters));
    }

    public void updateSteps() {

        // Filter Out the data
        for (int i = 0; i < 200; i += 4) {
            double sumX = 0, sumY = 0, sumZ = 0;
            for (int j = 0; j < 4 && i + j < 200; j++) {
                sumX += x.get(i + j);
                sumY += y.get(i + j);
                sumZ += z.get(i + j);
            }
            dataX.add(sumX / 4);
            dataY.add(sumY / 4);
            dataZ.add(sumZ / 4);

        }

        // clear the array list of input
        x.clear();
        y.clear();
        z.clear();

        // select the axis to work on
        int workingAxis = -1; // 0 = x, 1 = y, 2 = z

        double maxX = Collections.max(dataX), minX = Collections.min(dataX);
        double maxY = Collections.max(dataY), minY = Collections.min(dataY);
        double maxZ = Collections.max(dataZ), minZ = Collections.min(dataZ);

        double diffX = maxX - minX ;
        double diffY = maxY - minY;
        double diffZ = maxZ - minZ;

        double maxDiff = Math.max(diffX, Math.max(diffY, diffZ));

        if (maxDiff == diffX) {
            workingAxis = 0;
        } else if (maxDiff == diffY) {
            workingAxis = 1;
        } else if (maxDiff == diffZ) {
            workingAxis = 2;
        }

        // check how many steps now
        if (workingAxis == 0) {
            double average = (maxX + minX) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataX.get(j) && average < dataX.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        } else if (workingAxis == 1) {
            double average = (maxY + minY) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataY.get(j) && average < dataY.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        } else if (workingAxis == 2) {
            double average = (maxZ + minZ) / 2;
            for (int i = 0, j = 1; i < 49; i++, j++) {
                if (average > dataZ.get(j) && average < dataZ.get(i)) {
                    steps++;
                    displayCurrentsValues();
                }
            }

        }

        // clear the array list of filter
        dataX.clear();
        dataY.clear();
        dataZ.clear();


    }


    @SuppressLint("SetTextI18n")
    public void displayCurrentsValues() {
        stepsNumber.setText(Integer.toString(steps));
    }

}
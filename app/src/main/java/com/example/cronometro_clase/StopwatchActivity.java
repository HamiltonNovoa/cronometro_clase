package com.example.cronometro_clase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class StopwatchActivity extends Activity {

    private int vueltas = 0;
    private int seconds = 0;
    private boolean running;
    private ListView list;
    private ArrayList<String> tiempos = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        runTimer();
    }

    public void onClickStart(View view) {
        if (vueltas == 10) {
            tiempos.clear();
            arrayAdapter.notifyDataSetChanged();

            vueltas = 0;
            seconds = 0;
            running = true;
        } else {
            running = true;
        }
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {


        if (vueltas > 0) {
            tiempos.clear();
            arrayAdapter.notifyDataSetChanged();

            running = false;
            seconds = 0;
            vueltas = 0;
        } else {
            running = false;
            seconds = 0;
            vueltas = 0;
        }
    }


    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onClickVuelta(View view) {
        if (vueltas < 10 && running) {
            vueltas++;

            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tiempos);
            list = findViewById(R.id.list);
            list.setAdapter(arrayAdapter);


            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int secs = seconds % 60;
            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
            tiempos.add(vueltas + " - " + time);
            arrayAdapter.notifyDataSetChanged();

            if (vueltas == 10) {

                running = false;
            }
        }
    }
}
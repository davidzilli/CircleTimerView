package com.bravebeard.circletimerview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private CircleTimerView blue_timer;
    private CircleTimerView orange_timer;
    private CircleTimerView green_timer;
    private CircleTimerView purple_timer;

    private ArrayList<CircleTimerView> timer_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blue_timer = (CircleTimerView) findViewById(R.id.blue_timer);
        orange_timer = (CircleTimerView) findViewById(R.id.orange_timer);
        orange_timer.config(android.R.color.holo_orange_dark, android.R.color.holo_orange_light, 3, 5000, false);
        green_timer = (CircleTimerView) findViewById(R.id.green_timer);
        green_timer.config(android.R.color.holo_green_dark, android.R.color.holo_green_light, 40, 2000, true);
        purple_timer = (CircleTimerView) findViewById(R.id.purple_timer);
        purple_timer.config(android.R.color.holo_purple, android.R.color.holo_blue_light, 5, 4000, false);

        timer_list = new ArrayList<CircleTimerView>();
        timer_list.add(blue_timer);
        timer_list.add(orange_timer);
        timer_list.add(green_timer);
        timer_list.add(purple_timer);

        for (CircleTimerView timer : timer_list) {
            timer.start();
        }

        green_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green_timer.pause();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
//        for (CircleTimerView timer : timer_list) {
//            timer.pause();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

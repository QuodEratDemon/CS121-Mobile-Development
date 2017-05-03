package com.dealfaro.luca.KitchenTImer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    //recent time
    private int recent = 0;
    private int counter = 0;
    //store the recent time
    private int recent1_value = 0;
    private int recent2_value = 0;
    private int recent3_value = 0;
    //used to see if it was a set time or stopped time
    private boolean set_time = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        seconds += 60;
        //we are making a value
        set_time = true;
        displayTime();
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        if (seconds == 0){
            set_time = false;
        }
        displayTime();
    };

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            //check to see if we want to make a preset
            if (set_time == true){
                recent = seconds;
                display_recent_time();
                counter++;
            }
            timer.start();
        }
    }

    public void onClickStop(View v) {
        set_time = false;
        cancelTimer();
        displayTime();
    }

    //functional buttons for presets
    public void onClickRecent1(View v) {
        seconds = recent1_value;

        displayTime();
        onClickStart(null);
    }

    public void onClickRecent2(View v) {
        seconds = recent2_value;

        displayTime();
        onClickStart(null);
    }

    public void onClickRecent3(View v) {
        seconds = recent3_value;

        displayTime();
        onClickStart(null);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }

    //function to set the text/value of each preset
    private void display_recent_time() {
        int recent_time = recent;
        Button recent_1 = (Button) findViewById(R.id.recent_1);
        Button recent_2 = (Button) findViewById(R.id.recent_2);
        Button recent_3 = (Button) findViewById(R.id.recent_3);

        switch (counter){
            case 0:

                if (recent_time != recent2_value && recent_time != recent3_value && recent1_value != recent_time) {
                    recent1_value = recent_time;
                    int m = recent_time / 60;
                    int s = recent_time % 60;
                    recent_1.setText(String.format("%d:%02d", m, s));
                } else {
                    counter--;
                }
                break;
            case 1:

                if (recent_time != recent2_value && recent_time != recent3_value && recent1_value != recent_time) {
                    recent2_value = recent_time;
                    int m = recent_time / 60;
                    int s = recent_time % 60;
                    recent_2.setText(String.format("%d:%02d", m, s));
                } else {
                    counter--;
                }
                break;
            case 2:

                if (recent_time != recent2_value && recent_time != recent3_value && recent1_value != recent_time) {
                    recent3_value = recent_time;
                    int m = recent_time / 60;
                    int s = recent_time % 60;
                    recent_3.setText(String.format("%d:%02d", m, s));
                    counter = -1;
                } else {
                    counter--;
                }

                break;
            default:
                recent_1.setText("error");
                recent_2.setText("error");
                recent_3.setText("error");
                counter = -1;

        }
    }


}

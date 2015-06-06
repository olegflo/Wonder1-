package com.philips.lighting.quickstart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.philips.lighting.hue.SimpleHueController;

import java.util.Random;

public class MyApplicationActivity extends Activity {

    public static final String LOG_TAG = MyApplicationActivity.class.getSimpleName();

    private SimpleHueController simpleHueController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        Button randomButton;
        randomButton = (Button) findViewById(R.id.buttonRand);
        randomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                randomLights();
            }
        });

        simpleHueController = new SimpleHueController();
    }

    public void randomLights() {
        Random rand = new Random();
        simpleHueController.manageBrightness(rand.nextInt(100) + 1);
    }

    @Override
    protected void onDestroy() {
        simpleHueController.destroy();
        super.onDestroy();
    }

}

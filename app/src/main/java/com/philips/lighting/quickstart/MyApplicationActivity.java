package com.philips.lighting.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import thermometer.ThermometerDemoActivity;

public class MyApplicationActivity extends Activity {

    public static final String LOG_TAG = MyApplicationActivity.class.getSimpleName();

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
    }

    public void randomLights() {
        Intent intent = new Intent(getApplicationContext(), ThermometerDemoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

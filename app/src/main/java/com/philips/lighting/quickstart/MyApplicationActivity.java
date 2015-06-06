package com.philips.lighting.quickstart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.philips.lighting.hue.SimpleHueController;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * MyApplicationActivity - The starting point for creating your own Hue App.
 * Currently contains a simple view with a button to change your lights to random colours.  Remove this and add your own app implementation here! Have fun!
 *
 * @author SteveyO
 */
public class MyApplicationActivity extends Activity {
    private PHHueSDK phHueSDK;
    private static final int MAX_HUE = 65535;
    public static final String TAG = "QuickStart";

    private SimpleHueController simpleHueController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        phHueSDK = PHHueSDK.create();
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

    boolean flag;

    public void randomLights() {

        Random rand = new Random();

        simpleHueController.manageBrightness(rand.nextInt(100) + 1);

//        PHBridge bridge = phHueSDK.getSelectedBridge();
//
//        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
//        Random rand = new Random();
//
//
//        for (PHLight light : allLights) {
//
//
//            PHLightState lightState = new PHLightState();
//            // lightState.setHue(hue);
//            // lightState.setSaturation(saturation);
////            int brt = 254;
////            int brt1 = ;
//
//
//            lightState.setBrightness(flag ? 0 : 254);
//            flag = !flag;
//
//
//            // PHLightState lightState = new PHLightState();
//            // lightState.setHue(rand.nextInt(MAX_HUE));
//            // To validate your lightstate is valid (before sending to the bridge) you can use:
//            // String validState = lightState.validateState();
//            bridge.updateLightState(light, lightState, listener);
//            //  bridge.updateLightState(light, lightState);   // If no bridge response is required then use this simpler form.
//        }
    }

    // If you want to handle the response from the bridge, create a PHLightListener object.
    PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
        }

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Log.w(TAG, "Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {
        }

        @Override
        public void onReceivingLightDetails(PHLight arg0) {
        }

        @Override
        public void onReceivingLights(List<PHBridgeResource> arg0) {
        }

        @Override
        public void onSearchComplete() {
        }
    };

    @Override
    protected void onDestroy() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        if (bridge != null) {

            if (phHueSDK.isHeartbeatEnabled(bridge)) {
                phHueSDK.disableHeartbeat(bridge);
            }

            phHueSDK.disconnect(bridge);
            super.onDestroy();
        }
    }
}

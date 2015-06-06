package com.philips.lighting.hue;

import android.util.Log;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.List;
import java.util.Map;

/**
 * @author Oleg Soroka
 * @date 6/6/15
 * <p/>
 */
public class SimpleHueController implements SimpleHueApi {

    public static final String LOG_TAG = SimpleHueController.class.getSimpleName();

    private PHHueSDK phHueSDK;

    private static final int MAX_BRIGHTNESS = 254;
    private static final int MIN_BRIGHTNESS = 0;

    public SimpleHueController() {
        phHueSDK = PHHueSDK.create();
    }

    private void processBrightness(int val) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights) {
            PHLightState lightState = new PHLightState();
            lightState.setBrightness(val);
            bridge.updateLightState(light, lightState, listener);
        }
    }

    private PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
        }

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Log.w(LOG_TAG, "Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {
            Log.w(LOG_TAG, "onError");
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
    public void manageBrightness(int externalBrightness) {
        Log.d(LOG_TAG, "manageBrightness");
        int lampBrightness = MAX_BRIGHTNESS * (100 - externalBrightness) / 100;
        Log.d(LOG_TAG, "in " + externalBrightness);
        Log.d(LOG_TAG, "out " + lampBrightness);
        processBrightness(lampBrightness);
    }

}

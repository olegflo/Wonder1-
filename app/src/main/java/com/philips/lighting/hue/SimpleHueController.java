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

import timber.log.Timber;

/**
 * @author Oleg Soroka
 * @date 6/6/15
 * <p/>
 */
public class SimpleHueController implements SimpleHueApi {

    public static final String LOG_TAG = SimpleHueController.class.getSimpleName();

    private PHHueSDK phHueSDK;

    private static final int HUE_MAX_BRIGHTNESS = 254;
    private static final int HUE_MIN_BRIGHTNESS = 0;

    private static final int MY_MAX_BRIGHTNESS = 100;
    private static final int MY_MIN_BRIGHTNESS = 0;

    public SimpleHueController() {
        phHueSDK = PHHueSDK.create();
    }

    public void destroy() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        if (bridge == null) {
            return;
        }
        if (phHueSDK.isHeartbeatEnabled(bridge)) {
            phHueSDK.disableHeartbeat(bridge);
        }

        phHueSDK.disconnect(bridge);
    }

    private void processBrightness(int val) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights) {
            PHLightState lightState = new PHLightState();
            lightState.setBrightness(val);
            lightState.setOn(val != HUE_MIN_BRIGHTNESS);
            bridge.updateLightState(light, lightState, listener);
        }
    }

    private PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
            Timber.d("onSuccess");
        }

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Timber.d("Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {
            Timber.e("onError");
        }

        @Override
        public void onReceivingLightDetails(PHLight arg0) {
            Timber.d("onReceivingLightDetails: " + arg0);
        }

        @Override
        public void onReceivingLights(List<PHBridgeResource> arg0) {
            Timber.d("onReceivingLights: " + arg0);
        }

        @Override
        public void onSearchComplete() {
            Timber.d("onSearchComplete");
        }

    };

    @Override
    public void manageBrightness(int externalBrightness) {
        Log.d(LOG_TAG, "manageBrightness");
        if (externalBrightness > MY_MAX_BRIGHTNESS) {
            externalBrightness = MY_MAX_BRIGHTNESS;
        } else if (externalBrightness < MY_MIN_BRIGHTNESS) {
            externalBrightness = MY_MIN_BRIGHTNESS;
        }
        int lampBrightness = HUE_MAX_BRIGHTNESS * (MY_MAX_BRIGHTNESS - externalBrightness) / MY_MAX_BRIGHTNESS;
        Log.d(LOG_TAG, "in " + externalBrightness);
        Log.d(LOG_TAG, "out " + lampBrightness);
        processBrightness(lampBrightness);
    }

}

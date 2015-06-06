package com.philips.lighting.hue;

/**
 * @author Oleg Soroka
 * @date 6/6/15
 * <p/>
 */
public interface SimpleHueApi {

    /**
     * Expects external brightness value in range [1..100]
     *
     * @param externalBrightness
     */
    void manageBrightness(int externalBrightness);

    void manageHue(int externalHue);

//    void setBrightness(int val);

//    void switchOn();
//
//    void switchOff();

}

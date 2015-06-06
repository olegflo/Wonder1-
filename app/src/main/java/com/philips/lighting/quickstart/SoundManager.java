package com.philips.lighting.quickstart;

import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * @author Oleg Soroka
 * @date 6/6/15
 * <p/>
 */
public class SoundManager {

    private static final int UPPER_THRESHOLD = 75;
    private static final int LOWER_THRESHOLD = 10;

    private int currVal;
    private int prevVal;

    public SoundManager() {
    }

    public void playSound(int percentage) {
        currVal = percentage;

        if (prevVal > LOWER_THRESHOLD && currVal < LOWER_THRESHOLD) {
            crossLowerThreshold();
        }

        if (prevVal < UPPER_THRESHOLD && currVal > UPPER_THRESHOLD) {
            crossUpperThreshold();
        }

        prevVal = currVal;
    }

    private void crossLowerThreshold() {
        System.out.println("crossLowerThreshold");
        playShutdown();
    }

    private void crossUpperThreshold() {
        System.out.println("crossUpperThreshold");
        playStartup();
    }

    private void playShutdown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toneGenerator.stopTone();
            }
        }).start();
    }

    public void playStartup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toneGenerator.stopTone();
            }
        }).start();
    }

}
package com.medeuz.translatorapp.model;

import android.content.Context;

import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Vocalizer;

public class TranslatorModel {

    private static final String TAG = "TranslatorModel";

    /**
     * API Key for Yandex Speech kit
     */
    private static final String SPEECH_API_KEY = "ec5c14c0-c5f0-405d-8a82-b63c862807e0";

    /**
     * Vocalizer object of Yandex SpeechKit to pronounce text
     */
    private Vocalizer mVocalizer;

    public TranslatorModel(Context context) {
        SpeechKit.getInstance().configure(context.getApplicationContext(), SPEECH_API_KEY);
    }

    /**
     * Pronounce passed text with SpeechKit
     *
     * @param countryCode of passed text
     * @param text that would be pronounced
     */
    public void vocalizeText(String countryCode, String text) {
        resetVocalizer();
        mVocalizer = Vocalizer.createVocalizer(countryCode, text, true);
        //ToDo set listener to Vocalizer and handle it in Presenter
        mVocalizer.start();
    }

    /**
     * Make vocalizer variable free and stop current pronouncing
     */
    private void resetVocalizer() {
        if (mVocalizer != null) {
            mVocalizer.cancel();
            mVocalizer = null;
        }
    }


}

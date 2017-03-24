package com.medeuz.translatorapp.model;

import android.content.Context;

import com.medeuz.translatorapp.utils.Utils;

import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Vocalizer;

import static com.medeuz.translatorapp.utils.Utils.CountryCode;

public class TranslatorModel {

    private static final String TAG = "TranslatorModel";

    /**
     * API Key for Yandex Speech kit
     */
    private static final String SPEECH_API_KEY = "ec5c14c0-c5f0-405d-8a82-b63c862807e0";

    /**
     * country code of from what language we translate
     */
    private CountryCode mFromCountryCode;

    /**
     * country code of to what language we translate
     */
    private CountryCode mToCountryCode;

    /**
     * Vocalizer object of Yandex SpeechKit to pronounce text
     */
    private Vocalizer mVocalizer;

    public TranslatorModel(Context context) {
        SpeechKit.getInstance().configure(context.getApplicationContext(), SPEECH_API_KEY);
        setFromCountryCode(CountryCode.RU);
        setToCountryCode(CountryCode.EN);
    }

    /**
     * Pronounce passed text with SpeechKit
     *
     * @param countryCode of passed text
     * @param text that would be pronounced
     */
    public void vocalizeText(CountryCode countryCode, String text) {
        resetVocalizer();
        mVocalizer = Vocalizer.createVocalizer(countryCode.toString(), text, true);
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

    public void setFromCountryCode(CountryCode code) {
        this.mFromCountryCode = code;
    }

    public CountryCode getFromCountryCode() {
        return this.mFromCountryCode;
    }

    public void setToCountryCode(CountryCode code) {
        this.mToCountryCode = code;
    }

    public CountryCode getToCountryCode() {
        return this.mToCountryCode;
    }

    public String getTranslationLangs() {
        return getFromCountryCode() + "-" + getToCountryCode();
    }

}

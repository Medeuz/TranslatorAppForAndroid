package com.medeuz.translatorapp.presenter;


public interface ITranslatorPresenter {

    /**
     * Get translation of passed text
     *
     * @param lang codes of text and translation (ru-en for example)
     * @param text text for translation
     */
    void getTranslate(String lang, String text);

    /**
     * Pronounce passed text with SpeechKit
     *
     * @param countryCode of passed text (ru for example)
     * @param text for pronouncing
     */
    void pronounceText(String countryCode, String text);

    /**
     * Toggle language translation
     */
    void toggleLanguage();

}

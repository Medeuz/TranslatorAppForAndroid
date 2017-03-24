package com.medeuz.translatorapp.presenter;


public interface ITranslatorPresenter {

    /**
     * Get translation of passed text
     *
     * @param text text for translation
     */
    void getTranslate(String text);

    /**
     * Pronounce passed text with SpeechKit
     *
     * @param text for pronouncing
     */
    void pronounceText(String text);

    /**
     * Toggle language translation
     */
    void toggleLanguage();

}

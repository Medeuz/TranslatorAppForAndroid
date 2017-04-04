package com.medeuz.translatorapp.presenter;


public interface ITranslatorPresenter {

    /**
     * Get translation of passed text
     *
     * @param text text for translation
     */
    void getTranslate(String text);

    /**
     * Pronounce passed text with SpeechKit in original language
     *
     * @param text for pronouncing
     */
    void pronounceNotTranslatedText(String text);

    /**
     * Pronounce passed text with SpeechKit in language of translation
     *
     * @param text for pronouncing
     */
    void pronounceTranslatedText(String text);

    /**
     * Toggle language translation
     */
    void toggleLanguage();

    void addTranslationToFavorite();

}

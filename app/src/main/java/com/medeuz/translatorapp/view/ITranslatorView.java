package com.medeuz.translatorapp.view;


import com.medeuz.translatorapp.utils.Utils;

public interface ITranslatorView {

    /**
     * Toggle language in UI
     *
     * @param fromLang countryCode of original text
     * @param toLang countryCode of translation
     */
    void toggleLanguage(Utils.CountryCode fromLang, Utils.CountryCode toLang);

    /**
     * Show translated text in UI
     *
     * @param originalText inputted by user
     * @param translatedText which retrieved from translation API
     */
    void showTranslate(String originalText, String translatedText);

    /**
     * Show loading of translation in UI
     */
    void showTranslationLoading();

    /**
     * Hide loading if translation in UI
     */
    void hideTranslationLoading();

    /**
     * Shows passed error in UI
     *
     * @param e raised error object
     */
    void showError(Throwable e);

    void toggleFavorite(boolean isFavorite);

}

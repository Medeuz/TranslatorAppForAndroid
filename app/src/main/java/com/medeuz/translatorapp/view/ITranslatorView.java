package com.medeuz.translatorapp.view;


import com.medeuz.translatorapp.utils.Utils;

public interface ITranslatorView {

    void toggleLanguage(Utils.CountryCode fromLang, Utils.CountryCode toLang);

    void showTranslate(String originalText, String translatedText);

    void showTranslationLoading();

    void hideTranslationLoading();

    void showError(Throwable e);

}

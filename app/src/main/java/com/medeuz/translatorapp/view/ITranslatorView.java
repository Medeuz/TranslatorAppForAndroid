package com.medeuz.translatorapp.view;


public interface ITranslatorView {

    void showTranslate(String originalText, String translatedText);

    void showTranslationLoading();

    void hideTranslationLoading();

    void showError(Throwable e);

}

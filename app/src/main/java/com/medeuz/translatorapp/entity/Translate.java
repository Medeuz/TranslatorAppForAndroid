package com.medeuz.translatorapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmModel;

public class Translate implements RealmModel {

    /**
     * Country code of languages (xx-xx)
     */
    @SerializedName("lang")
    private String mLanguagesCountryCode;

    /**
     * Text of translation
     */
    @SerializedName("text")
    private List<String> mTranslatation;

    /**
     * Is translation in favorites list
     */
    private boolean isFavorite;

    public Translate(String lang, List<String> translation) {
        this.mLanguagesCountryCode = lang;
        this.mTranslatation = translation;
        this.isFavorite = false;
    }

    /**
     * Retrieves country code of languages
     *
     * @return String of country code (xx-xx) or null
     */
    public String getLanguagesCountryCode() {
        return mLanguagesCountryCode;
    }

    /**
     * Retrieves translated text
     *
     * @return String or null
     */
    public List<String> getTranslatation() {
        return mTranslatation;
    }

    /**
     * Retrieves flag is translation in Favorite list
     *
     * @return boolean isFavorite flag
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Sets boolean favorite flag
     *
     * @param favorite boolean flag is translation in favorite list
     */
    public void setToFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

}

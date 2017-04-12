package com.medeuz.translatorapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Translate extends RealmObject {

    /**
     * Not translated text, original
     */
    @PrimaryKey
    private String mOriginalText;

    /**
     * Country code of languages (xx-xx)
     */
    @SerializedName("lang")
    private String mLanguagesCountryCode;

    /**
     * Text of translation
     */
    @SerializedName("text")
    private RealmList<RealmString> mTranslatation;

    /**
     * Is translation in favorites list
     */
    private boolean isFavorite;

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
    public List<RealmString> getTranslatation() {
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

    public void setOriginalText(String originalText) {
        this.mOriginalText = originalText;
    }

    public String getOriginalText() {
        return this.mOriginalText;
    }

}

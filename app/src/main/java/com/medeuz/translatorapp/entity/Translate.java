package com.medeuz.translatorapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Translate {

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

    public Translate(String lang, List<String> translatation) {
        this.mLanguagesCountryCode = lang;
        this.mTranslatation = translatation;
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

}

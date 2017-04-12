package com.medeuz.translatorapp.entity;

import io.realm.RealmObject;

/**
 * Because realm cannot save List<String> we should make this... "костыль"
 */
public class RealmString extends RealmObject {

    private String string;

    public RealmString(String string) {
        this.string = string;
    }

    public RealmString() {}

    @Override
    public String toString() {
        return string;
    }

}

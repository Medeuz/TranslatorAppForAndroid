package com.medeuz.translatorapp.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.medeuz.translatorapp.entity.RealmString;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.RealmList;

/**
 * Deserializer for GSON for RealmList object
 */
public class RealmListDeserializer implements JsonDeserializer<RealmList<RealmString>> {

    @Override
    public RealmList<RealmString> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        List<String> stringList = context.deserialize(json, List.class);
        RealmList<RealmString> result = new RealmList<>();
        for (String s : stringList) {
            result.add(new RealmString(s));
        }
        
        return result;
    }
}

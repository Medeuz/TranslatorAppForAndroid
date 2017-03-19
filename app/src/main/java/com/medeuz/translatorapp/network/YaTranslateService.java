package com.medeuz.translatorapp.network;


import com.medeuz.translatorapp.entity.Translate;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface YaTranslateService {

    @FormUrlEncoded
    @POST("translate")
    Observable<Translate> getTranslate(@Query("lang") String lang, @Field("text") String text);
}

package com.medeuz.translatorapp.network;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String API_VERSION = "v1.5";
    private static final String TRANSLATE_API_BASE_URL = "https://translate.yandex.net/api/" + API_VERSION + "/tr.json/";
    private static final String TRASLATE_API_KEY = "trnsl.1.1.20170318T131426Z.39d7e38db987eb47.c5ba36c480b72385822e020046540e3a15b1bc4f";

    /**
     * creates instance of http client for retrofit
     * with sending of current api key in every request
     */
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                //Adding to every request key parameter with API key
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", TRASLATE_API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(TRANSLATE_API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static YaTranslateService createTranslateService() {
        return createService(YaTranslateService.class);
    }

}
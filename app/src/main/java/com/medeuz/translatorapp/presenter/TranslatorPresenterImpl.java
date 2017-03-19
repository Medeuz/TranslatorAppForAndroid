package com.medeuz.translatorapp.presenter;


import android.util.Log;

import com.medeuz.translatorapp.entity.Translate;
import com.medeuz.translatorapp.network.ServiceGenerator;
import com.medeuz.translatorapp.network.YaTranslateService;
import com.medeuz.translatorapp.view.ITranslatorView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TranslatorPresenterImpl implements ITranslatorPresenter {

    private static final String TAG = "TranslatorPresenter";

    /**
     * Retrofit Yandex Translate API service
     */
    private YaTranslateService mYaTranslateService = ServiceGenerator.createTranslateService();

    /**
     * View of translate screen (TranslatorFragment)
     */
    private ITranslatorView mView;

    public TranslatorPresenterImpl(ITranslatorView view) {
        this.mView = view;
    }

    @Override
    public void getTranslate(String lang, String text) {

        mView.showTranslationLoading();

        Observable<Translate> translateObservable = mYaTranslateService.getTranslate(lang, text);
        translateObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Translate>() {
                    @Override
                    public void onCompleted() {
                        mView.hideTranslationLoading();

                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);

                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Translate translate) {
                        mView.showTranslate(text, translate.getTranslatation().get(0));

                        Log.d(TAG, translate.getTranslatation().get(0));
                    }
                });
    }

}

package com.medeuz.translatorapp.presenter;


import android.content.Context;
import android.util.Log;

import com.medeuz.translatorapp.entity.Translate;
import com.medeuz.translatorapp.model.TranslatorModel;
import com.medeuz.translatorapp.network.ServiceGenerator;
import com.medeuz.translatorapp.network.YaTranslateService;
import com.medeuz.translatorapp.utils.Utils;
import com.medeuz.translatorapp.view.ITranslatorView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

    /**
     * Model of translate screen
     */
    private TranslatorModel mModel;

    public TranslatorPresenterImpl(Context context, ITranslatorView view) {
        this.mView = view;
        this.mModel = new TranslatorModel(context);
    }

    @Override
    public void getTranslate(String text) {
        mView.showTranslationLoading();

        String langs = mModel.getTranslationLangs();
        Observable<Translate> translateObservable = mYaTranslateService.getTranslate(langs, text);
        translateObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Translate>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);

                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideTranslationLoading();

                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onNext(Translate translate) {
                        mView.showTranslate(text, translate.getTranslatation().get(0));

                        Log.d(TAG, translate.getTranslatation().get(0));
                    }
                });
    }

    @Override
    public void pronounceText(String countryCode, String text) {
        mModel.vocalizeText(countryCode, text);
    }

    @Override
    public void toggleLanguage() {
        Utils.CountryCode temp = mModel.getFromCountryCode();
        mModel.setFromCountryCode(mModel.getToCountryCode());
        mModel.setToCountryCode(temp);
        mView.toggleLanguage(mModel.getFromCountryCode(), mModel.getToCountryCode());
    }

}

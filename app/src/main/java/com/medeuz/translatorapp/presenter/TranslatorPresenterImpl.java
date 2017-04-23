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
import io.realm.Realm;

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

    /**
     * Realm instance to restore and save translations
     */
    private Realm mRealm;

    public TranslatorPresenterImpl(Context context, ITranslatorView view) {
        this.mView = view;
        this.mModel = new TranslatorModel(context);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void getTranslate(String text) {
        mView.showTranslationLoading();

        Translate translate = getCachedTranslate(text);
        if (translate != null) {
            mModel.setTranslate(translate);
            mView.showTranslate(text, translate.getTranslatation().get(0).toString());
            mView.toggleFavorite(translate.isFavorite());
            mView.hideTranslationLoading();
            return;
        }

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
                        mView.showTranslate(text, translate.getTranslatation().get(0).toString());
                        mView.toggleFavorite(translate.isFavorite());
                        //Make saving only if translated and original text is not equal
                        if (!translate.getTranslatation().get(0).toString().equals(text)) {
                            translate.setOriginalText(text);
                            saveTranslationToRealm(translate);
                        }
                        mModel.setTranslate(translate);

                        Log.d(TAG, translate.getTranslatation().get(0).toString());
                    }
                });
    }

    /**
     * Save translate object into Realm database
     *
     * @param translate object
     */
    private void saveTranslationToRealm(Translate translate) {
        if (!translate.isManaged()) {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(translate);
            mRealm.commitTransaction();
        }
    }

    /**
     * Returns cached translation of text or null
     *
     * @param text to be translated
     * @return Translate object or null
     */
    private Translate getCachedTranslate(String text) {
        return mRealm.where(Translate.class)
                .equalTo("mOriginalText", text)
                .findFirst();
    }

    @Override
    public void pronounceNotTranslatedText(String text) {
        mModel.vocalizeText(mModel.getFromCountryCode(), text);
    }

    @Override
    public void pronounceTranslatedText(String text) {
        mModel.vocalizeText(mModel.getToCountryCode(), text);
    }

    @Override
    public void toggleLanguage() {
        Utils.CountryCode temp = mModel.getFromCountryCode();
        mModel.setFromCountryCode(mModel.getToCountryCode());
        mModel.setToCountryCode(temp);
        mView.toggleLanguage(mModel.getFromCountryCode(), mModel.getToCountryCode());
    }

    @Override
    public void switchTranslationInFavorite() {
        boolean isFavorite = !mModel.getTranslate().isFavorite();
        mRealm.beginTransaction();
        mModel.getTranslate().setToFavorite(isFavorite);
        mRealm.commitTransaction();
        saveTranslationToRealm(mModel.getTranslate());
        mView.toggleFavorite(isFavorite);
    }

    @Override
    public void onResume() {
        if (mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onPause() {
        mRealm.close();
    }

}

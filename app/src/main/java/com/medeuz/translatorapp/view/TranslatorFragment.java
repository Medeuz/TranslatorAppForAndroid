package com.medeuz.translatorapp.view;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.medeuz.translatorapp.R;
import com.medeuz.translatorapp.presenter.ITranslatorPresenter;
import com.medeuz.translatorapp.presenter.TranslatorPresenterImpl;

public class TranslatorFragment extends Fragment implements ITranslatorView, TextView.OnEditorActionListener, View.OnKeyListener {

    public static final String TAG = "TranslatorFragment";

    /**
     * Presenter of Translator screen, see ITranslatorPresenter interface
     */
    private ITranslatorPresenter translatorPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_translator, container, false);

        EditText editText = (EditText) root.findViewById(R.id.translate_input_et);
        editText.setOnEditorActionListener(this);
        editText.setOnKeyListener(this);

        translatorPresenter = new TranslatorPresenterImpl(this);
        //translatorPresenter.getTranslate("ru-en", "Привет, меня зовут Стас. Рад знакомству!");

        return root;
    }

    @Override
    public void showTranslate(String originalText, String translatedText) {

    }

    @Override
    public void showTranslationLoading() {

    }

    @Override
    public void hideTranslationLoading() {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            translatorPresenter.getTranslate("ru-en", textView.getText().toString());
        return false;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (KeyEvent.ACTION_UP == keyEvent.getAction())
            if (i == 66) {
                translatorPresenter.getTranslate("ru-en", ((TextView) view).getText().toString());
            }
        return false;
    }
}

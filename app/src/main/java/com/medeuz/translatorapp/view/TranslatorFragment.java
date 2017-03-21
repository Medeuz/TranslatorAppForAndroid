package com.medeuz.translatorapp.view;


import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.medeuz.translatorapp.R;
import com.medeuz.translatorapp.presenter.ITranslatorPresenter;
import com.medeuz.translatorapp.presenter.TranslatorPresenterImpl;

public class TranslatorFragment extends Fragment implements ITranslatorView {

    public static final String TAG = "TranslatorFragment";

    /**
     * Presenter of Translator screen, see ITranslatorPresenter interface
     */
    private ITranslatorPresenter mTranslatorPresenter;

    /**
     * EditText input for text that would be translated
     */
    private EditText mTranslateInputEt;

    /**
     * Button for translation of text in mTranslateInputEt
     */
    private ImageButton mTranslateBtn;

    /**
     * Button for pronouncing of text in mTranslateInputEt
     */
    private ImageButton mPronounceBtn;

    /**
     * Button to clear EditText mTranslateInputEt
     */
    private ImageButton mClearTextBtn;

    /**
     * TextView for translated text from mTranslateInputEt
     */
    private TextView mTranslatedTextTv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_translator, container, false);

        mTranslateInputEt = (EditText) root.findViewById(R.id.translate_input_et);
        mTranslateBtn = (ImageButton) root.findViewById(R.id.translate_btn);
        mPronounceBtn = (ImageButton) root.findViewById(R.id.pronounce_btn);
        mClearTextBtn = (ImageButton) root.findViewById(R.id.clear_input_txt_btn);
        mTranslatedTextTv = (TextView) root.findViewById(R.id.translated_text_tv);

        mTranslatorPresenter = new TranslatorPresenterImpl(getActivity(), this);

        initActionBar();
        setListeners();

        return root;
    }

    private void setListeners() {
        mTranslateBtn.setOnClickListener(view ->
                mTranslatorPresenter.getTranslate("ru-en", mTranslateInputEt.getText().toString())
        );
        mPronounceBtn.setOnClickListener(view ->
                mTranslatorPresenter.pronounceText("ru", mTranslateInputEt.getText().toString())
        );
        mClearTextBtn.setOnClickListener(view ->
                mTranslateInputEt.setText("")
        );
    }

    /**
     * Sets ActionBar custom view with button to change languages
     */
    private void initActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(R.layout.translator_toolbar);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
    }

    @Override
    public void showTranslate(String originalText, String translatedText) {
        mTranslatedTextTv.setText(translatedText);
    }

    @Override
    public void showTranslationLoading() {

    }

    @Override
    public void hideTranslationLoading() {

    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

}

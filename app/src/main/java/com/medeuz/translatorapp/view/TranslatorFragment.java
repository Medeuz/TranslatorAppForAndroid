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
import com.medeuz.translatorapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TranslatorFragment extends Fragment implements ITranslatorView {

    public static final String TAG = "TranslatorFragment";

    /**
     * Presenter of Translator screen, see ITranslatorPresenter interface
     */
    private ITranslatorPresenter mTranslatorPresenter;

    /**
     * Unbinder object for ButterKnife unbinding
     */
    private Unbinder mUnbinder;
    /**
     * EditText input for text that would be translated
     */
    @BindView(R.id.translate_input_et)
    EditText mTranslateInputEt;

    /**
     * Button for translation of text in mTranslateInputEt
     */
    @BindView(R.id.translate_btn)
    ImageButton mTranslateBtn;

    /**
     * Button for pronouncing of text in mTranslateInputEt
     */
    @BindView(R.id.pronounce_btn)
    ImageButton mPronounceBtn;

    /**
     * Button to clear EditText mTranslateInputEt
     */
    @BindView(R.id.clear_input_txt_btn)
    ImageButton mClearTextBtn;

    /**
     * TextView for translated text from mTranslateInputEt
     */
    @BindView(R.id.translated_text_tv)
    TextView mTranslatedTextTv;

    /**
     * ImageButton for language translation change in ActionBar
     */
    private ImageButton mActionBarLanguageBtn;

    /**
     * TextView for language name for translation
     */
    private TextView mActionBarFromLangTv;

    /**
     * TextView for language name for translated result
     */
    private TextView mActionBarToLangTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_translator, container, false);

        mTranslatorPresenter = new TranslatorPresenterImpl(getActivity(), this);

        mUnbinder = ButterKnife.bind(this, root);
        initActionBar();
        setListeners();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setListeners() {
        mTranslateBtn.setOnClickListener(view ->
                mTranslatorPresenter.getTranslate(mTranslateInputEt.getText().toString())
        );
        mPronounceBtn.setOnClickListener(view ->
                mTranslatorPresenter.pronounceText("ru", mTranslateInputEt.getText().toString())
        );
        mClearTextBtn.setOnClickListener(view ->
                {
                    mTranslateInputEt.setText("");
                    mTranslatedTextTv.setText("");
                }
        );
        mActionBarLanguageBtn.setOnClickListener(view ->
            mTranslatorPresenter.toggleLanguage()
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
            mActionBarLanguageBtn
                    = (ImageButton) actionBar.getCustomView()
                    .findViewById(R.id.lang_change_btn);
            mActionBarFromLangTv
                    = (TextView) actionBar.getCustomView()
                    .findViewById(R.id.translate_from_language_tv);
            mActionBarToLangTv
                    = (TextView) actionBar.getCustomView()
                    .findViewById(R.id.translate_to_language_tv);
        }
    }

    @Override
    public void toggleLanguage(Utils.CountryCode fromLang, Utils.CountryCode toLang) {
        CharSequence temp = mActionBarFromLangTv.getText();
        mActionBarFromLangTv.setText(mActionBarToLangTv.getText());
        mActionBarToLangTv.setText(temp);
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

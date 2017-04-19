package com.medeuz.translatorapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
     * Extra argument for passing title of fragment
     */
    public static final String EXTRA_FRAGMENT_TITLE = "com.medeuz.translatorapp.extra.FRAGMENT_TITLE";

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
     * Button for pronouncing translated text
     */
    @BindView(R.id.pronounce_translated_btn)
    ImageButton mPronounceTranslatedBtn;

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
     * ProgressBar to show that translation loading
     */
    @BindView(R.id.translation_load_pb)
    ProgressBar mTranslationLoadPb;

    /**
     * Button to add translation to favorites list
     */
    @BindView(R.id.switch_in_favorite_btn)
    ImageButton mSwitchTranslationInFavorite;

    /**
     * ImageButton for language translation change in ActionBar
     */
    @BindView(R.id.lang_change_btn)
    ImageButton mActionBarLanguageBtn;

    /**
     * TextView for language name for translation
     */
    @BindView(R.id.translate_from_language_tv)
    TextView mActionBarFromLangTv;

    /**
     * TextView for language name for translated result
     */
    @BindView(R.id.translate_to_language_tv)
    TextView mActionBarToLangTv;

    public static TranslatorFragment newInstance(String title) {
        TranslatorFragment fragment = new TranslatorFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_FRAGMENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_translator, container, false);

        mTranslatorPresenter = new TranslatorPresenterImpl(getActivity(), this);

        mUnbinder = ButterKnife.bind(this, root);

        setListeners();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTranslatorPresenter != null) {
            mTranslatorPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTranslatorPresenter != null) {
            mTranslatorPresenter.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setListeners() {
        mTranslateBtn.setOnClickListener(view -> {
                    mTranslatedTextTv.setText("");
                    mTranslatorPresenter.getTranslate(mTranslateInputEt.getText().toString());
                });
        mPronounceBtn.setOnClickListener(view ->
                mTranslatorPresenter.pronounceNotTranslatedText(mTranslateInputEt.getText().toString())
        );
        mPronounceTranslatedBtn.setOnClickListener(view ->
                mTranslatorPresenter.pronounceTranslatedText(mTranslatedTextTv.getText().toString())
        );
        mClearTextBtn.setOnClickListener(view -> {
                    mTranslateInputEt.setText("");
                    mTranslatedTextTv.setText("");
                });
        mActionBarLanguageBtn.setOnClickListener(view ->
            mTranslatorPresenter.toggleLanguage()
        );
        mSwitchTranslationInFavorite.setOnClickListener(view ->
            mTranslatorPresenter.switchTranslationInFavorite()
        );
    }

    @Override
    public void toggleLanguage(Utils.CountryCode fromLang, Utils.CountryCode toLang) {
        //ToDo animate switch
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
        //ToDo animate this shit
        mTranslationLoadPb.setVisibility(View.VISIBLE);
        mPronounceTranslatedBtn.setVisibility(View.GONE);
        mSwitchTranslationInFavorite.setVisibility(View.GONE);
    }

    @Override
    public void hideTranslationLoading() {
        //ToDo animate this shit
        mTranslationLoadPb.setVisibility(View.GONE);
        mPronounceTranslatedBtn.setVisibility(View.VISIBLE);
        mSwitchTranslationInFavorite.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void toggleFavorite(boolean isFavorite) {
        //ToDo animate switch
        if (isFavorite) {
            mSwitchTranslationInFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            mSwitchTranslationInFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

}

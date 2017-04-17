package com.medeuz.translatorapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.medeuz.translatorapp.R;
import com.medeuz.translatorapp.adapter.TranslateAdapter;
import com.medeuz.translatorapp.entity.Translate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

public class HistoryFragment extends Fragment {

    /**
     * Extra argument for passing is fragment shows history or favorite translations
     */
    public static final String EXTRA_IS_FAVORITE_LIST = "com.medeuz.translatorapp.extra.IS_FAVORITE_LIST";

    /**
     * Extra argument for passing title of fragment
     */
    public static final String EXTRA_FRAGMENT_TITLE = "com.medeuz.translatorapp.extra.FRAGMENT_TITLE";

    //    @BindView(R.id.search_et)
//    EditText searchEt;

    @BindView(R.id.history_list_rv)
    RecyclerView historyListRv;

    private Unbinder mUnbinder;

    /**
     * Flag which shows which list shows fragment, history or favorite
     */
    private boolean isFavoriteList;

    /**
     * newInstance constructor for creating fragment with arguments
     *
     * @param title which would be set in action bar
     * @param isFavoriteList what list of translations should be in fragment
     * @return HistoryFragment object with arguments
     */
    public static HistoryFragment newInstance(String title, boolean isFavoriteList) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_IS_FAVORITE_LIST, isFavoriteList);
        args.putString(EXTRA_FRAGMENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFavoriteList = getArguments().getBoolean(EXTRA_IS_FAVORITE_LIST, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);

        mUnbinder = ButterKnife.bind(this, root);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Translate> translateList;

        if (isFavoriteList) {
            translateList = realm.where(Translate.class).equalTo("isFavorite", true).findAll();
        } else {
            translateList = realm.where(Translate.class).findAll();
        }

        TranslateAdapter adapter = new TranslateAdapter(
                translateList,
                true
        );
        historyListRv.setAdapter(adapter);
        historyListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}

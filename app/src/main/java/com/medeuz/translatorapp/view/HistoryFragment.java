package com.medeuz.translatorapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medeuz.translatorapp.R;
import com.medeuz.translatorapp.adapter.TranslateAdapter;
import com.medeuz.translatorapp.entity.Translate;

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

    @BindView(R.id.history_list_rv)
    RecyclerView historyListRv;

    private Unbinder mUnbinder;

    /**
     * Flag which shows which list shows fragment, history or favorite
     */
    private boolean isFavoriteList;

    /**
     * Realm instance to retrieve data from Realm
     */
    private Realm mRealm;

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
    public void onResume() {
        super.onResume();
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFavoriteList = getArguments().getBoolean(EXTRA_IS_FAVORITE_LIST, false);
        }
        mRealm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);

        mUnbinder = ButterKnife.bind(this, root);

        RealmResults<Translate> translateList;

        if (isFavoriteList) {
            translateList = mRealm.where(Translate.class).equalTo("isFavorite", true).findAll();
        } else {
            translateList = mRealm.where(Translate.class).findAll();
        }

        TranslateAdapter adapter = new TranslateAdapter(
                translateList,
                true
        );
        historyListRv.setAdapter(adapter);
        historyListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        setListeners(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void setListeners(TranslateAdapter adapter) {
        adapter.setOnDeleteClickListener((view, position) -> {
            Translate translate = adapter.getItem(position);
            if (translate != null) {
                mRealm.beginTransaction();
                translate.deleteFromRealm();
                mRealm.commitTransaction();
            }
        });

        adapter.setOnFavoriteClickListener((view, position) -> {
            Translate translate = adapter.getItem(position);
            if (translate != null) {
                boolean isFavorite = !translate.isFavorite();
                mRealm.beginTransaction();
                translate.setToFavorite(isFavorite);
                mRealm.commitTransaction();
            }
        });
    }

}

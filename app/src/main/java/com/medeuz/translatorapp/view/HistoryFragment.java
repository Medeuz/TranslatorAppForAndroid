package com.medeuz.translatorapp.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.medeuz.translatorapp.R;

import butterknife.BindView;

public class HistoryFragment extends Fragment {

    @BindView(R.id.search_et)
    EditText searchEt;

    @BindView(R.id.history_list_rv)
    RecyclerView historyListRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);



        return root;
    }

}

package com.medeuz.translatorapp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.medeuz.translatorapp.entity.Translate;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TranslateAdapter extends RealmRecyclerViewAdapter<Translate, TranslateAdapter.ViewHolder> {

    public TranslateAdapter(@Nullable OrderedRealmCollection<Translate> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}

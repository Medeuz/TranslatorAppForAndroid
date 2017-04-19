package com.medeuz.translatorapp.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.medeuz.translatorapp.R;
import com.medeuz.translatorapp.entity.Translate;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TranslateAdapter extends RealmRecyclerViewAdapter<Translate, TranslateAdapter.ViewHolder> {

    /**
     * Listener for Delete button click
     */
    private OnItemClickListener onDeleteClickListener;

    /**
     * Listener for Favorite button click
     */
    private OnItemClickListener onFavoriteClickListener;

    public TranslateAdapter(@Nullable OrderedRealmCollection<Translate> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TranslateAdapter.ViewHolder(inflater.inflate(R.layout.translate_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Translate translate = getItem(position);
        if (translate != null) {
            holder.originalTextTv.setText(translate.getOriginalText());
            holder.translateTextTv.setText(translate.getTranslatation().get(0).toString());
            holder.languagesCountryCodesTv.setText(translate.getLanguagesCountryCode().toUpperCase());
            if (translate.isFavorite()) {
                holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                holder.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
        }
    }

    /**
     * Setter for OnDeleteClickListener
     *
     * @param listener implementation of OnItemClickListener
     */
    public void setOnDeleteClickListener(OnItemClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    /**
     * Setter for OnFavoriteClickListener
     *
     * @param listener implementation of OnItemClickListener
     */
    public void setOnFavoriteClickListener(OnItemClickListener listener) {
        this.onFavoriteClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Button for switching is translation in favorite list
         */
        ImageButton favoriteBtn;

        /**
         * Button for remove translation from history/cache
         */
        ImageButton deleteBtn;

        /**
         * TextView for original text which was translated
         */
        TextView originalTextTv;

        /**
         * TextView for translated text
         */
        TextView translateTextTv;

        /**
         * TextView for country codex fo translation (ru-en, en-ru)
         */
        TextView languagesCountryCodesTv;

        ViewHolder(View itemView) {
            super(itemView);
            favoriteBtn = (ImageButton) itemView.findViewById(R.id.favorite_btn);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete_btn);
            originalTextTv = (TextView) itemView.findViewById(R.id.original_text_tv);
            translateTextTv = (TextView) itemView.findViewById(R.id.translated_text_tv);
            languagesCountryCodesTv = (TextView) itemView.findViewById(R.id.langs_tv);

            deleteBtn.setOnClickListener((v) -> {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onItemClick(v, getAdapterPosition());
                }
            });

            favoriteBtn.setOnClickListener((v) -> {
                if (onFavoriteClickListener != null) {
                    onFavoriteClickListener.onItemClick(v, getAdapterPosition());
                }
            });

        }
    }

}

package com.aditiaryohandoko.ugd_lib_x_yyyyy.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.R;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.databinding.AdapterCardGameBinding;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Game;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.stream.Collectors;

public class AdapterCardGame extends RecyclerView.Adapter<AdapterCardGame.CardGameViewHolder>
        implements Filterable {

    private List<Game> listData;
    private List<Game> filteredListData;

    private OnCardClickListener listener;

    public AdapterCardGame(List<Game> listData, OnCardClickListener listener) {
        this.listData = listData;
        this.filteredListData = listData;
        this.listener = listener;
    }

    @NonNull
    @Override

    public CardGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardGameViewHolder(AdapterCardGameBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardGameViewHolder holder, int position) {
        holder.bind(filteredListData.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredListData.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    fr.values = listData;
                } else {
                    fr.values = listData.stream().filter(data ->
                            data.getTitle().toLowerCase().contains(charSequence))
                            .collect(Collectors.toList());
                }
                return fr;
            }

            @SuppressLint("NotifyDataSetChanged")
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredListData = (List<Game>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class CardGameViewHolder extends RecyclerView.ViewHolder {
        private AdapterCardGameBinding binding;
        private OnCardClickListener listener;

        public CardGameViewHolder(AdapterCardGameBinding binding, OnCardClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(Game game) {
            binding.title.setText(game.getTitle());
            binding.price.setText(game.getFormattedPrice());

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .dontTransform()
                    .placeholder(R.drawable.ic_launcher_background)
                    .priority(Priority.IMMEDIATE)
                    .encodeFormat(Bitmap.CompressFormat.PNG)
                    .format(DecodeFormat.DEFAULT);

            Glide.with(binding.getRoot())
                    .applyDefaultRequestOptions(requestOptions)
                    .load(game.getUrlImage())
                    .into(binding.thumbNail);

            binding.buttonBuy.setOnClickListener(v -> {
                listener.onClick(game);
            });

            binding.showChart.setOnClickListener(v -> {
                listener.onChartClick(game);
            });

            binding.expand.setOnClickListener(v -> {
                listener.onDescriptionClick(game.getDescription());
            });
        }
    }
}

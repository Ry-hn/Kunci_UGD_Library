package com.aditiaryohandoko.ugd_lib_x_yyyyy.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.R;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.databinding.AdapterCartBinding;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.PurchasedGame;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class AdapterCardCart extends RecyclerView.Adapter<AdapterCardCart.CartViewHolder> {

    private List<PurchasedGame> purchasedGameList;
    private OnCartClickListener listener;

    public AdapterCardCart(List<PurchasedGame> purchasedGameList, OnCartClickListener listener) {
        this.purchasedGameList = purchasedGameList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(
                AdapterCartBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false
                ), listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(purchasedGameList.get(position));
    }

    @Override
    public int getItemCount() {
        return purchasedGameList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private AdapterCartBinding binding;
        private OnCartClickListener lister;

        public CartViewHolder(AdapterCartBinding binding, OnCartClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.lister = listener;
        }

        public void bind(PurchasedGame purchasedGame) {
            binding.title.setText(purchasedGame.getGame().getTitle());
            String _temp = purchasedGame.getGame().getCurrency() + purchasedGame.getTotalBayar();
            binding.price.setText(_temp);
            binding.jumlahBeli.setText(String.valueOf(purchasedGame.getJumlah()));

            binding.buttonKurang.setOnClickListener(v -> {
                this.lister.onKurang(purchasedGame, getAdapterPosition());
            });

            binding.buttonTambah.setOnClickListener(v -> {
                this.lister.onTambah(purchasedGame, getAdapterPosition());
            });

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .dontAnimate()
                    .dontTransform()
                    .placeholder(R.drawable.ic_launcher_background)
                    .priority(Priority.IMMEDIATE)
                    .encodeFormat(Bitmap.CompressFormat.PNG)
                    .format(DecodeFormat.DEFAULT);

            Glide.with(binding.getRoot())
                    .applyDefaultRequestOptions(requestOptions)
                    .load(purchasedGame.getGame().getUrlImage())
                    .into(binding.thumbNail);
        }
    }
}

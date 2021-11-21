package com.aditiaryohandoko.ugd_lib_x_yyyyy.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Game;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.PurchasedGame;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Response;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.UserProfile;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.repositories.MainRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private MainRepository repo;

    private MutableLiveData<List<Game>> gameListLiveData;
    private MutableLiveData<String> errMsg;

    private MutableLiveData<List<PurchasedGame>> keranjangBelanja;

    private MutableLiveData<UserProfile> userProfileLiveData;

    {
        gameListLiveData = new MutableLiveData<>(new ArrayList<>());
        keranjangBelanja = new MutableLiveData<>(new ArrayList<>());
        userProfileLiveData = new MutableLiveData<>(null);
        errMsg = new MutableLiveData<>("");
    }

    @Inject
    public MainViewModel(MainRepository repo) {
        this.repo = repo;
    }

    public void loadResponse() {
        try {
            if (this.gameListLiveData.getValue().size() == 0) {
                LiveData<Response> temp = this.repo.getResponse();
                this.gameListLiveData.setValue(temp.getValue().getData());
                this.errMsg.setValue(temp.getValue().getMessage() + " size: " + temp.getValue().getData().size());
            }

        } catch (Exception e) {
            e.printStackTrace();
            errMsg.setValue(e.getMessage());
        }
    }

    public void scanQRUserProfile(String jsonString) throws Exception {
        Gson gson = new Gson();
        UserProfile userProfile = gson.fromJson(jsonString, UserProfile.class);
        userProfileLiveData.setValue(userProfile);
    }

    public void addToCart(Game game) {
        PurchasedGame purchasedGame = cariGame(game.getId());

        if (purchasedGame != null) {
            purchasedGame.setJumlah(purchasedGame.getJumlah() + 1);
        } else {
            if (keranjangBelanja.getValue() != null) {
                keranjangBelanja.getValue().add(new PurchasedGame(game, 1));
            }
        }

        keranjangBelanja.setValue(keranjangBelanja.getValue());
    }

    public void clearCart() {
        keranjangBelanja.setValue(new ArrayList<>());
    }

    public void addOneFromCart(Game game) {
        PurchasedGame purchasedGame = cariGame(game.getId());

        if (purchasedGame != null) {
            purchasedGame.setJumlah(purchasedGame.getJumlah() + 1);
        }
    }

    public void removeFromCart(Game game) {
        PurchasedGame purchasedGame = cariGame(game.getId());

        if (purchasedGame != null) {
            if (purchasedGame.getJumlah() == 1) {
                List<PurchasedGame> tempKeranjangBelanja = keranjangBelanja.getValue();
                tempKeranjangBelanja.removeIf(data -> data.getGame().getId() == game.getId());

                keranjangBelanja.setValue(tempKeranjangBelanja);
            } else {
                purchasedGame.setJumlah(purchasedGame.getJumlah() - 1);
            }
        }
    }

    private PurchasedGame cariGame(int id) {
        if (keranjangBelanja.getValue() != null) {
            List<PurchasedGame> belanjaan = keranjangBelanja.getValue();
            for (PurchasedGame game : belanjaan) {
                if (game.getGame().getId() == id)
                    return game;
            }
        }
        return null;
    }

    public LiveData<List<Game>> getGameListLiveData() {
        return gameListLiveData;
    }

    public LiveData<List<PurchasedGame>> getKeranjangBelanja() {
        return keranjangBelanja;
    }

    public LiveData<String> getErrMsg() {
        return errMsg;
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }
}

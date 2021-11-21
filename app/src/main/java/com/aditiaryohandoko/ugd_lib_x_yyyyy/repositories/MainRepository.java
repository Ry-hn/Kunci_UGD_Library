package com.aditiaryohandoko.ugd_lib_x_yyyyy.repositories;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Game;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.Response;
import com.aditiaryohandoko.ugd_lib_x_yyyyy.model.UserProfile;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

public class MainRepository {

    private final Context context;

    @Inject
    public MainRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<Response> getResponse() throws Exception {
        Gson gson = new Gson();
        Response responseFromJson = gson.fromJson(loadJson(), Response.class);
        return new MutableLiveData<>(responseFromJson);
    }

    private Reader loadJson() throws Exception {
        AssetManager am = this.context.getAssets();
        InputStream is = am.open("datagame.json");
        return new InputStreamReader(is);
    }
}

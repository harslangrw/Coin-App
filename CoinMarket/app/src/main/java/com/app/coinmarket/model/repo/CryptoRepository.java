package com.app.coinmarket.model.repo;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.app.coinmarket.model.data.LogoModel;
import com.app.coinmarket.model.interfaces.ApiInterface;
import com.app.coinmarket.model.data.CryptoListData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CryptoRepository {
    private static ApiInterface myInterface;
    private final MutableLiveData<CryptoListData> listOfMovies = new MutableLiveData<>();
    private final MutableLiveData<Map<String, String>> image = new MutableLiveData<>();
    private static CryptoRepository cryptoRepository;
    public static CryptoRepository getInstance() {
        if (cryptoRepository == null) {
            cryptoRepository = new CryptoRepository();
        }
        return cryptoRepository;
    }

    private CryptoRepository() {
        myInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);;
    }

    public MutableLiveData<CryptoListData> getCryptocurrencies() {
        Call<CryptoListData> listOfMovieOut = myInterface.getCryptocurrencies();
        listOfMovieOut.enqueue(new Callback<CryptoListData>() {
            @Override
            public void onResponse(@NonNull Call<CryptoListData> call, @NonNull Response<CryptoListData> response) {
                listOfMovies.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CryptoListData> call, @NonNull Throwable t) {
                listOfMovies.postValue(null);
            }
        });
        return listOfMovies;
    }

    public MutableLiveData<Map<String,String>> getLogo(int id) {
        Map<String, String> map = new HashMap<>();
        Call<LogoModel> listOfMovieOut = myInterface.getLogo(id);
        listOfMovieOut.enqueue(new Callback<LogoModel>() {
            @Override
            public void onResponse(@NonNull Call<LogoModel> call, @NonNull Response<LogoModel> response) {
                String param = call.request().url().queryParameter("id");
                if (response.body() != null) {
                    map.put(param, Objects.requireNonNull(Objects.requireNonNull(response.body().data.get(param)).logo));
                }
                image.setValue(map);
            }

            @Override
            public void onFailure(@NonNull Call<LogoModel> call, @NonNull Throwable t) {
                image.postValue(null);
            }
        });
        return image;
    }

}
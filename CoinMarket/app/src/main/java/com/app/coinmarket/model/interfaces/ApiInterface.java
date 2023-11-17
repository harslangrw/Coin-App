package com.app.coinmarket.model.interfaces;

import com.app.coinmarket.model.data.CryptoListData;
import com.app.coinmarket.model.data.LogoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("v1/cryptocurrency/listings/latest?limit=20")
    Call<CryptoListData> getCryptocurrencies();
    @GET("v2/cryptocurrency/info?")
    Call<LogoModel> getLogo(@Query("id") int id);
}
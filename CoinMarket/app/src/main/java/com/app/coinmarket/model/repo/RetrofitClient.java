package com.app.coinmarket.model.repo;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://pro-api.coinmarketcap.com/";
    private static final String API_KEY = "9caf599f-4098-4367-9c54-51d28524031e";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request request = original.newBuilder().header("X-CMC_PRO_API_KEY", API_KEY).method(original.method(), original.body()).build();
                return chain.proceed(request);
            });
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

        }
        return retrofit;
    }
}
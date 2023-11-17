package com.app.coinmarket.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.coinmarket.model.repo.CryptoRepository;
import com.app.coinmarket.model.data.CryptoListData;

import java.util.Map;

public class ViewModelCrypto extends AndroidViewModel {
    private final CryptoRepository repository;
    public ViewModelCrypto(@NonNull Application application) {
        super(application);
        repository = CryptoRepository.getInstance();
    }
    public MutableLiveData<CryptoListData> getCryptocurrencies() {
        return repository.getCryptocurrencies();
    }
    public MutableLiveData<Map<String,String>> getLogo(int id) {
        return repository.getLogo(id);
    }
}
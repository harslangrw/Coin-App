package com.app.coinmarket.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.app.coinmarket.R;
import com.app.coinmarket.databinding.ActivityMainBinding;
import com.app.coinmarket.model.data.CryptoListData;
import com.app.coinmarket.model.interfaces.ItemClickCallback;
import com.app.coinmarket.view.adapter.ListAdapter;
import com.app.coinmarket.viewmodel.ViewModelCrypto;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ItemClickCallback {
    ActivityMainBinding binding;
    private Context context;
    int count = 0;
    Map<String, String> map = new HashMap<>();
    List<CryptoListData> cryptoList;
    ProgressDialog progressDialog;
    ViewModelCrypto viewModelCrypto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        initViewModel();
        setObserver();
        setFilterSpinner();
     }

    private void setObserver() {
        viewModelCrypto.getCryptocurrencies().observe(this, movieResponse -> {
            cryptoList = movieResponse.data;
            setAdapter();
        });
    }

    private void initViewModel() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        viewModelCrypto= new ViewModelCrypto(getApplication());
        viewModelCrypto = viewModelProvider.get(viewModelCrypto.getClass());
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    private void setFilterSpinner() {
        List<String> filterList = new ArrayList<>();
        filterList.add("Filter");
        filterList.add("By Price");
        filterList.add("Volume");
        binding.spinner.setAdapter(new ArrayAdapter<>(this, R.layout.tv_spinner, filterList));
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (cryptoList != null) {
                    List<CryptoListData> models = new ArrayList<>(cryptoList);
                    if (filterList.get(i).equals("Filter")) {
                        binding.recyclerview.setAdapter(new ListAdapter(context, cryptoList, map, MainActivity.this));
                    } else {
                        if (filterList.get(i).equals("By Price")) {
                            models.sort(Comparator.comparingDouble(CryptoListData::getPrice));
                        } else {
                            models.sort(Comparator.comparingDouble(CryptoListData::getVolumeChange));
                        }
                        binding.recyclerview.setAdapter(new ListAdapter(context, models, map, MainActivity.this));
                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAdapter() {
        for (int i = 0; i < cryptoList.size(); i++) {
            viewModelCrypto.getLogo(cryptoList.get(i).id).observe(this, movieResponse -> {
                for ( String key : movieResponse.keySet() ) {
                    map.put(key,movieResponse.get(key));
                }
                count += 1;
                if (count == cryptoList.size()) {
                    progressDialog.dismiss();
                    binding.recyclerview.setAdapter(new ListAdapter(context, cryptoList, map, MainActivity.this));
                    setCardData(cryptoList.get(0));
                }
            });
        }
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void setCardData(CryptoListData model) {
        int color= R.color.red;
        String s="";
        if (model.quote.USD.volume_change_24h >0) {
            color=R.color.green;
            s="+";
        }
        binding.tvVolumeChange.setText(s + String.format("%.2f", model.quote.USD.volume_change_24h) + "");
        binding.ivGraph.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));
        binding.tvVolumeChange.setTextColor(ContextCompat.getColor(context,color));
        binding.tvName.setText(model.name);
        binding.tvSymbol.setText(model.symbol);
        binding.tvTotal.setText("$ " + String.format("%.2f", model.quote.USD.price) + " USD");
        Picasso.get().load(map.get(String.valueOf(model.id))).placeholder(R.drawable.baseline_image_24).error(R.drawable.baseline_image_24).into(binding.ivLogo);
    }
    @Override
    public void onClick(CryptoListData model) {
        setCardData(model);
    }
}
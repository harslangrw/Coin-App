package com.app.coinmarket.model.data;

import java.util.List;

public class CryptoListData {
    public Quote quote;
    public String name, symbol;
    public List<CryptoListData> data;
    public int id;

    public double getPrice() {
        return quote.USD.price;
    }
    public double getVolumeChange() {
        return quote.USD.volume_change_24h;
    }
}

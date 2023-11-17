package com.app.coinmarket.model.data;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class LogoModel {
    @SerializedName("data")
    public Map<String, LogoDataModel> data;
}

package com.app.coinmarket.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.coinmarket.R;
import com.app.coinmarket.model.data.CryptoListData;
import com.app.coinmarket.model.interfaces.ItemClickCallback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    Context context;
    List<CryptoListData> list;
    Map<String, String> map;
    ItemClickCallback clickCallback;
    public ListAdapter(Context context, List<CryptoListData> list, Map<String, String> map, ItemClickCallback clickCallback) {
        this.context = context;
        this.list = list;
        this.map = map;
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CryptoListData model = list.get(position);
        int color;
        String s;
        if (model.quote.USD.volume_change_24h > 0) {
            color = R.color.green;
            s = "+";
        } else {
            color = R.color.red;
            s = "";
        }
        holder.tvVolumeChange.setText(s + String.format("%.2f", model.quote.USD.volume_change_24h) + "");
        holder.ivGraph.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
        holder.tvVolumeChange.setTextColor(ContextCompat.getColor(context, color));
        holder.tvName.setText(model.name);
        holder.tvSymbol.setText(model.symbol);
        holder.tvPrice.setText("$ " + String.format("%.2f", model.quote.USD.price) + " USD");
        Picasso.get().load(map.get(String.valueOf(model.id))).placeholder(R.drawable.baseline_image_24).error(R.drawable.baseline_image_24).into(holder.ivLogo);
        holder.itemView.setOnClickListener(view -> clickCallback.onClick(model));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSymbol, tvName, tvPrice, tvVolumeChange;
        ImageView ivGraph, ivLogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSymbol = itemView.findViewById(R.id.tv_symbol);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvVolumeChange = itemView.findViewById(R.id.tv_volume_change);
            ivGraph = itemView.findViewById(R.id.iv_graph);
            ivLogo = itemView.findViewById(R.id.iv_logo);
        }
    }

}
package com.tma.stockmarket.ui.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tma.stockmarket.R;
import com.tma.stockmarket.ui.model.ExchangeRate;
import com.tma.stockmarket.ui.navigation.ClickListener;

import java.text.DecimalFormat;
import java.util.List;


public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateAdapter.MyViewHolder> {
    private Context mContext;
    private List<ExchangeRate> mListExchange;
    private List<ExchangeRate> mListExchangeOld;
    private ClickListener clickListener;


    public ExchangeRateAdapter(Context mContext, List<ExchangeRate> mListExchange, List<ExchangeRate> mListExchangeOld, ClickListener clickListener) {
        this.mContext = mContext;
        this.mListExchange = mListExchange;
        this.clickListener = clickListener;
        this.mListExchangeOld = mListExchangeOld;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ExchangeRate exchangeRate = mListExchange.get(position);
        String str = "";
        DecimalFormat df = new DecimalFormat("#.#####");
        if (mListExchangeOld.size() != 0) {
            ExchangeRate RateOld = mListExchangeOld.get(position);
            if (RateOld.getPrice() >exchangeRate.getPrice()) {
                holder.tvPriceChange.setTextColor(ContextCompat.getColor(mContext, R.color.tvred));
                Double intTam = (RateOld.getPrice() - exchangeRate.getPrice());
                str = "-" + String.valueOf(df.format(intTam));
            } else {
                holder.tvPriceChange.setTextColor(ContextCompat.getColor(mContext, R.color.tvgreen));
                Double intTam = (exchangeRate.getPrice() - RateOld.getPrice());
                str = "+" + String.valueOf(df.format(intTam));
            }
        }
        holder.tvPriceChange.setText(str);
        holder.tvCurrency.setText(exchangeRate.getSymbol());
        holder.tvPrice.setText(String.valueOf(exchangeRate.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mListExchange.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrency, tvPrice, tvPriceChange;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvCurrency = (TextView) itemView.findViewById(R.id.tvCurrency);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvPriceChange = (TextView) itemView.findViewById(R.id.tvPriceChange);
        }
    }
}

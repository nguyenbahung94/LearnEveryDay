package com.tma.stockmarket.ui.view.activity;

import com.tma.stockmarket.ui.model.ExchangeRate;

import java.util.List;


public interface ExchangeRateView {
    void listExchangeRate(List<ExchangeRate> exchangeRateList);

    void message(String message);

    void failed();
}

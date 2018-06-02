package com.tma.stockmarket.ui.interactor.ExchangeRate;

import android.content.Context;

import com.tma.stockmarket.R;
import com.tma.stockmarket.api.GetExchangeRate;
import com.tma.stockmarket.ui.interactor.ExchangeRate.viewinteractor.ExchangeRateInteractor;
import com.tma.stockmarket.ui.model.ExchangeRate;
import com.tma.stockmarket.utils.url;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ExchangeRateInteractorIpm implements ExchangeRateInteractor {


    @Override
    public void getExchangeRate(Retrofit retrofit, Context context, final callback call) {
        retrofit.create(GetExchangeRate.class).getExchangeRate(addTypeRequest(context), url.API_2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExchangeRate[]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException || e instanceof SocketTimeoutException || e instanceof IOException) {
                            call.failed(1);
                        }
                    }

                    @Override
                    public void onNext(ExchangeRate[] exchangeRates) {
                        converStringArray(exchangeRates, call);
                    }
                });
    }

    private void converStringArray(ExchangeRate[] exchangeRates, callback call) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        Collections.addAll(exchangeRateList, exchangeRates);
        call.resultExchangeRate(exchangeRateList);
    }

    private String addTypeRequest(Context context) {
        String[] tam = context.getResources().getStringArray(R.array.type_request);
        String pairs = "";
        for (String aTam : tam) {
            pairs += aTam + ",";
        }
        return pairs;
    }
}



























/*
 "NZDJPY", "GBPCAD", "GBPNZD", "GBPAUD", "AUDNZD", "USDSEK", "EURSEK", "EURNOK", "USDNOK",
                "USDMXN", "AUDCHF", "EURNZD", "USDZAR", "ZARJPY", "USDTRY", "EURTRY", "NZDCHF", "CADCHF",
                "NZDCAD", "TRYJPY", "USDCNH", "XAUUSD", "XAGUSD", "USDEUR", "USDGBP", "USDAUD", "USDNZD",
                "USDXAU", "USDXAG", "EURMXN", "EURZAR", "EURCNH", "EURXAU", "EURXAG", "JPYUSD", "JPYEUR",
                "JPYGBP", "JPYCHF", "JPYAUD", "JPYCAD", "JPYNZD", "JPYSEK", "JPYNOK", "JPYMXN", "JPYZAR",
                "JPYTRY", "JPYCNH", "JPYXAU", "JPYXAG", "GBPEUR", "GBPSEK", "GBPNOK", "GBPMXN", "GBPZAR",
                "GBPTRY", "GBPCNH", "GBPXAU", "GBPXAG", "CHFUSD", "CHFEUR", "CHFGBP", "CHFAUD", "CHFCAD",
                "CHFNZD", "CHFSEK", "CHFNOK", "CHFMXN", "CHFZAR", "CHFTRY", "CHFCNH", "CHFXAU", "CHFXAG",
                "AUDEUR", "AUDGBP", "AUDSEK", "AUDNOK", "AUDMXN", "AUDZAR", "AUDTRY", "AUDCNH", "AUDXAU",
                "AUDXAG", "CADUSD", "CADEUR", "CADGBP", "CADAUD", "CADNZD", "CADSEK", "CADNOK", "CADMXN",
                "CADZAR", "CADTRY", "CADCNH", "CADXAU", "CADXAG", "NZDEUR", "NZDGBP", "NZDAUD", "NZDSEK",
                "NZDNOK", "NZDMXN", "NZDZAR", "NZDTRY", "NZDCNH", "NZDXAU", "NZDXAG", "SEKUSD", "SEKEUR",
                "SEKJPY", "SEKGBP", "SEKCHF", "SEKAUD", "SEKCAD", "SEKNZD", "SEKNOK", "SEKMXN", "SEKZAR",
                "SEKTRY", "SEKCNH", "SEKXAU", "SEKXAG", "NOKUSD", "NOKEUR", "NOKJPY", "NOKGBP", "NOKCHF",
                "NOKAUD", "NOKCAD", "NOKNZD", "NOKSEK", "NOKMXN", "NOKZAR", "NOKTRY", "NOKCNH", "NOKXAU",
                "NOKXAG", "MXNUSD", "MXNEUR", "MXNJPY", "MXNGBP", "MXNCHF", "MXNAUD", "MXNCAD", "MXNNZD",
                "MXNSEK", "MXNNOK", "MXNZAR", "MXNTRY", "MXNCNH", "MXNXAU", "MXNXAG", "ZARUSD", "ZAREUR",
                "ZARGBP", "ZARCHF", "ZARAUD", "ZARCAD", "ZARNZD", "ZARSEK", "ZARNOK", "ZARMXN", "ZARTRY",
                "ZARCNH", "ZARXAU", "ZARXAG", "TRYUSD", "TRYEUR", "TRYGBP", "TRYCHF", "TRYAUD", "TRYCAD",
                "TRYNZD", "TRYSEK", "TRYNOK", "TRYMXN", "TRYZAR", "TRYCNH", "TRYXAU", "TRYXAG", "CNHUSD",
                "CNHEUR", "CNHJPY", "CNHGBP", "CNHCHF", "CNHAUD", "CNHCAD", "CNHNZD", "CNHSEK", "CNHNOK",
                "CNHMXN", "CNHZAR", "CNHTRY", "CNHXAU", "CNHXAG", "XAUEUR", "XAUJPY", "XAUGBP", "XAUCHF",
                "XAUAUD", "XAUCAD", "XAUNZD", "XAUSEK", "XAUNOK", "XAUMXN", "XAUZAR", "XAUTRY", "XAUCNH",
                "XAUXAG", "XAGEUR", "XAGJPY", "XAGGBP", "XAGCHF", "XAGAUD", "XAGCAD", "XAGNZD", "XAGSEK",
                "XAGNOK", "XAGMXN", "XAGZAR", "XAGTRY", "XAGCNH", "XAGXAU"*/
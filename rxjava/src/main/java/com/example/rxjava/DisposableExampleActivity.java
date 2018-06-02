package com.example.rxjava;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class DisposableExampleActivity extends AppCompatActivity {
    private static final String TAG = DisposableExampleActivity.class.getSimpleName();
    Button btn;
    TextView textView;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposable_example);
        btn = findViewById(R.id.btnClick);
        textView = findViewById(R.id.tvShow);
        btn.setOnClickListener(v -> {
            textView.setVisibility(View.VISIBLE);
            Observable.just("Goodbye World!")
                    .delay(3, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s->textView.setText(s));

            //simple example using Flowable
          //  testFlowable();
        });
        RxView.clicks(findViewById(R.id.btnRxView))
                .map(o->1)
                .scan(0,(total,next)->total+next)
                .subscribe(i->textView.setText(String.valueOf(i)));


    }
    //
    private void testFlowable() {

        Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);

        observable.reduce(50, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer t1, Integer t2) {
                return t1 + t2;
            }
        }).subscribe(getObserver());

    }

    private SingleObserver<Integer> getObserver() {

        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer value) {
                textView.append(" onSuccess : value : " + value);
                textView.append("/");
                Log.d(TAG, " onSuccess : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append("/");
                Log.d(TAG, " onError : " + e.getMessage());
            }
        };
    }

    //Observable.from()
}

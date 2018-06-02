package com.tma.stockmarket.ui.main.fragment;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerExchangeRateFragnentComponent;
import com.tma.stockmarket.di.module.MainViewModule;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.main.adapter.ExchangeRateAdapter;
import com.tma.stockmarket.ui.model.ExchangeRate;
import com.tma.stockmarket.ui.navigation.ClickListener;
import com.tma.stockmarket.ui.presenter.ExchangeRatePresenterIpm;
import com.tma.stockmarket.ui.view.activity.ExchangeRateView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class ExchangeRateFragment extends Fragment implements ExchangeRateView {
    @Inject
    ExchangeRatePresenterIpm exchangeRatePresenterIpm;
    @Inject
    Retrofit retrofit;
    private List<ExchangeRate> listExchange;
    private List<ExchangeRate> listExchangeOld;
    private RecyclerView recycleExchangeRate;
    private ExchangeRateAdapter rateAdapter;
    private FloatingActionButton mFloatButton;
    private CoordinatorLayout mCoordinatorLayout;
    private CountDownTimer mCountDownTimer;
    private ProgressDialog progressDialog;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerExchangeRateFragnentComponent.builder()
                .netComponent(((App) getActivity().getApplication()).getmNetcomponent())
                .mainViewModule(new MainViewModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_exchange_rate, container, false);
        init();
        return mView;
    }

    private void init() {
        mFloatButton = (FloatingActionButton) mView.findViewById(R.id.btnFloataction);
        listExchange = new ArrayList<>();
        listExchangeOld = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
        exchangeRatePresenterIpm.getExchangeRate(retrofit, getContext());
        recycleExchangeRate = (RecyclerView) mView.findViewById(R.id.recycle_exchangerate);
        recycleExchangeRate.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycleExchangeRate.setLayoutManager(layoutManager);
        recycleExchangeRate.setItemAnimator(new DefaultItemAnimator());
        mCoordinatorLayout = (CoordinatorLayout) mView.findViewById(R.id.condinatorLayout);
        mFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout, R.string.refreshing, Snackbar.LENGTH_SHORT).show();
                exchangeRatePresenterIpm.getExchangeRate(retrofit, getContext());
            }
        });
    }

    //invisible view
    private void enterReveal() {
        int cx = mFloatButton.getMeasuredWidth() / 2;
        int cy = mFloatButton.getMeasuredHeight() / 2;

        int finalRadius = Math.max(mFloatButton.getWidth(), mFloatButton.getHeight()) / 2;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(mFloatButton, cx, cy, 0, finalRadius);
            anim.start();
            mFloatButton.setVisibility(View.VISIBLE);
        }

    }

    //visible view
//    private void exitReveal() {
//        int cx = mFloatButton.getMeasuredWidth() / 2;
//        int cy = mFloatButton.getMeasuredHeight() / 2;
//        int initialRadius = mFloatButton.getWidth() / 2;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Animator animator = ViewAnimationUtils.createCircularReveal(mFloatButton, cx, cy, initialRadius, 0);
//            animator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                    mFloatButton.setVisibility(View.INVISIBLE);
//
//                }
//            });
//            animator.start();
//        }
//    }

    private void eventRecycle() {
        rateAdapter = new ExchangeRateAdapter(getActivity(), listExchange, listExchangeOld, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        });
        recycleExchangeRate.setAdapter(rateAdapter);
        listExchangeOld.clear();
        listExchangeOld.addAll(listExchange);
    }

    @Override
    public void listExchangeRate(List<ExchangeRate> exchangeRateList) {
        listExchange.clear();
        listExchange.addAll(exchangeRateList);
        if (rateAdapter == null) {
            eventRecycle();
        }
        if (rateAdapter != null) {
            rateAdapter.notifyDataSetChanged();
        }
        mCountDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (getContext() != null) {
                    exchangeRatePresenterIpm.getExchangeRate(retrofit, getContext());
                }

            }
        }.start();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mFloatButton.post(new Runnable() {
            @Override
            public void run() {
                enterReveal();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

    }

    @Override
    public void message(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }
}

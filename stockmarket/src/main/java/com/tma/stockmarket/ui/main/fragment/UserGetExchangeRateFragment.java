package com.tma.stockmarket.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.stockmarket.R;
import com.tma.stockmarket.di.component.DaggerUserGetExchangeFragmentComponent;
import com.tma.stockmarket.di.module.UserGetExchangeRateModule;
import com.tma.stockmarket.ui.main.activity.App;
import com.tma.stockmarket.ui.presenter.UserGetExchangeRatePresenterIpm;
import com.tma.stockmarket.ui.view.activity.UserGetExchangeRateView;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class UserGetExchangeRateFragment extends Fragment implements UserGetExchangeRateView {
    @Inject
    Retrofit retrofit;
    @Inject
    UserGetExchangeRatePresenterIpm mPresenter;
    private Spinner spinnerFrom, spinnerTo;
    private EditText edtInputValue;
    private TextView tvResult;
    private Button btnOk;
    private ImageView imgFrom, imgTo;
    private int image[] = {R.drawable.usd, R.drawable.eur, R.drawable.aud, R.drawable.cad, R.drawable.chf, R.drawable.gbp, R.drawable.jpy};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerUserGetExchangeFragmentComponent.builder().userGetExchangeRateModule(new UserGetExchangeRateModule(this))
                .netComponent(((App) getActivity().getApplication()).getmNetcomponent()).build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_get_exchangerate, container, false);
        spinnerFrom = (Spinner) view.findViewById(R.id.spinFrom);
        spinnerTo = (Spinner) view.findViewById(R.id.spinTo);
        tvResult = (TextView) view.findViewById(R.id.tvresult);
        edtInputValue = (EditText) view.findViewById(R.id.edtInputValue);
        btnOk = (Button) view.findViewById(R.id.btnOk);
        imgFrom = (ImageView) view.findViewById(R.id.imgFrom);
        imgTo = (ImageView) view.findViewById(R.id.imgTo);
        event();

        Log.e("spiner from", spinnerFrom.getSelectedItem().toString());
        Log.e("spiner from", spinnerTo.getSelectedItem().toString());

        return view;
    }


    private void event() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_from, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
        spinnerFrom.setSelection(0);
        imgFrom.setImageResource(image[0]);
        spinnerTo.setSelection(1);
        imgTo.setImageResource(image[1]);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtInputValue.getText().toString().equals("") && edtInputValue.getText() != null) {
                    mPresenter.convertCurrency(retrofit, Integer.parseInt(edtInputValue.getText().toString()), spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgFrom.setImageResource(image[position]);
                autoCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imgTo.setImageResource(image[position]);
                autoCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        autoCall();
    }

    @Override
    public void resultExchangeRate(Double result) {
        tvResult.setText(String.valueOf(result));
    }

    @Override
    public void failed() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    public void autoCall() {
        edtInputValue.setText("1");
        mPresenter.convertCurrency(retrofit, 1, spinnerFrom.getSelectedItem().toString(), spinnerTo.getSelectedItem().toString());

    }
}

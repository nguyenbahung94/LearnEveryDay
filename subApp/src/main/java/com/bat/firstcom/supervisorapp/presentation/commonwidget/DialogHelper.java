package com.bat.firstcom.supervisorapp.presentation.commonwidget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bat.firstcom.supervisorapp.R;
import com.bat.firstcom.supervisorapp.common.FlowType;
import com.bat.firstcom.supervisorapp.common.Style;
import com.bat.firstcom.supervisorapp.datalayer.model.PSTDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Tung Phan on 4/26/2017.
 */
//TODO: this class using anti-pattern which is not make sure qualify the Open-close principle
public class DialogHelper {

    private Context context;
    private AlertDialog alertDialog;
    private LayoutInflater layoutInflater;
    private ActionPickerListener actionPickerListener;
    private OkDialogListener okDialogListener;
    private YesNoDialogListener yesNoDialogListener;

    public void setActionPickerListener(ActionPickerListener actionPickerListener) {
        this.actionPickerListener = actionPickerListener;
    }

    public void setOkDialogListener(OkDialogListener okDialogListener) {
        this.okDialogListener = okDialogListener;
    }

    public void setYesNoDialogListener(YesNoDialogListener yesNoDialogListener) {
        this.yesNoDialogListener = yesNoDialogListener;
    }

    public DialogHelper(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    public AlertDialog displayActionPickerDialog(PSTDatum pstDatum, FlowType flowType) {
        ListView listView = new ListView(context);
        listView.setAdapter(initDialogOptions(pstDatum, flowType));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (actionPickerListener != null) {
                actionPickerListener.onActionPickerItemClick(position, pstDatum);
            }
            this.alertDialog.dismiss();
        });
        this.alertDialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(listView)
                .create();
        this.alertDialog.show();
        return this.alertDialog;
    }

    private ArrayAdapterWithBottomLine initDialogOptions(PSTDatum pstDatum, FlowType flowType) {
        List<String> optionList = new ArrayList<>();
        optionList.add(pstDatum.getName());
        optionList.add(context.getString(R.string.scoring));
        if (flowType != null && flowType == FlowType.CHECKING) {
            optionList.add(context.getString(R.string.view_report));
            optionList.add(context.getString(R.string.edit_outlet));
        } else {
            optionList.add(context.getString(R.string.day_report_scoring));
        }
        optionList.add(context.getString(R.string.back));
        return new ArrayAdapterWithBottomLine(context
                , optionList);
    }

    public void hideDialog() {
        this.alertDialog.hide();
    }

    public AlertDialog displayYesNoDialog(String message, int positiveText, int negativeText) {
        //init custom dialog view
        ViewGroup viewGroup = (RelativeLayout) layoutInflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context).setView(viewGroup);
        AppCompatTextView textView = ButterKnife.findById(viewGroup, R.id.message);
        AppCompatTextView positiveBtn = ButterKnife.findById(viewGroup, R.id.tvPositive);
        AppCompatTextView negativeBtn = ButterKnife.findById(viewGroup, R.id.tvNegative);
        textView.setText(message);
        positiveBtn.setText(positiveText);
        negativeBtn.setText(negativeText);
        //show dialog
        AlertDialog yesNoAlertDialog = dialogbuilder.create();
        //set positive, negative click listener
        negativeBtn.setOnClickListener(v -> {
            yesNoAlertDialog.dismiss();
        });
        positiveBtn.setOnClickListener(v -> {
            if (yesNoDialogListener != null) {
                yesNoDialogListener.onYesButtonClick();
            }
            yesNoAlertDialog.dismiss();
        });
        yesNoAlertDialog.show();
        return alertDialog;
    }

    public AlertDialog displayOkDialog(int message, int positiveText) {
        //init custom dialog view
        ViewGroup viewGroup = (RelativeLayout) layoutInflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context).setView(viewGroup);
        AppCompatTextView textView = ButterKnife.findById(viewGroup, R.id.message);
        AppCompatTextView positiveBtn = ButterKnife.findById(viewGroup, R.id.tvPositive);
        AppCompatTextView negativeBtn = ButterKnife.findById(viewGroup, R.id.tvNegative);
        negativeBtn.setVisibility(View.GONE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.small_button_size),
                1.0f
        );
        positiveBtn.setLayoutParams(param);
        textView.setText(message);
        positiveBtn.setText(positiveText);
        //show dialog
        AlertDialog okAlertDialog = dialogbuilder.create();
        //set alertDialog listener
        positiveBtn.setOnClickListener(v -> {
            if (okDialogListener != null) {
                okDialogListener.onOkButtonClick();
            }
            okAlertDialog.dismiss();
        });
        okAlertDialog.show();
        return okAlertDialog;
    }

    public AlertDialog showDialog(String message) {
        //custom message view
        AppCompatTextView messageView = new AppCompatTextView(context);
        messageView.setText(message);
        messageView.setTextColor(ContextCompat.getColor(context, R.color.black));
        messageView.setTextSize(Style.NORMAL_TEXT_SIZE);
        messageView.setGravity(Gravity.CENTER);
        messageView.setPadding(0
                , (int) context.getResources().getDimension(R.dimen.padding_large), 0, 0);
        //show dialog
        AlertDialog showDialog = new AlertDialog.Builder(context)
                .setNegativeButton(context.getString(R.string.ok), (dialog, which) -> dialog.dismiss())
                .setView(messageView).create();
        showDialog.show();
        return showDialog;
    }

    public interface ActionPickerListener {
        void onActionPickerItemClick(int position, PSTDatum pstDatum);
    }

    public interface YesNoDialogListener {
        void onYesButtonClick();
    }

    public interface OkDialogListener {
        void onOkButtonClick();
    }
}

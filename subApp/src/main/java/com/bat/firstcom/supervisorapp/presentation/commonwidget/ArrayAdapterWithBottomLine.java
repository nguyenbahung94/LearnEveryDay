package com.bat.firstcom.supervisorapp.presentation.commonwidget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.bat.firstcom.supervisorapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by tung phan on 7/15/17.
 */

public class ArrayAdapterWithBottomLine extends ArrayAdapter<String> {

    private Context context;
    private List<String> strings = new ArrayList<>();

    public ArrayAdapterWithBottomLine(Context context, List<String> strings) {
        super(context, R.layout.product_spinner_dropdown_row, strings);
        this.context = context;
        this.strings = strings;
    }

    @Override
    public  @NonNull View getView(int position, View convertView,@NonNull ViewGroup parent) {
        View row = convertView;
        ItemHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.picked_option_layout, parent, false);
            holder = new ItemHolder();
            holder.textview = ButterKnife.findById(row, R.id.tvItem);
            row.setTag(holder);
        } else {
            holder = (ItemHolder) row.getTag();
        }
        holder.textview.setText(strings.get(position));
        return row;
    }

    private static class ItemHolder {
        AppCompatTextView textview;
    }
}

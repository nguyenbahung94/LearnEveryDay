package com.bat.firstcom.supervisorapp.presentation.login;

import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * Common adapter using for spinner, show all items except the last one.
 * It can give better user experience when user click on spinner and see the drop down list
 * without the default item of the spinner.
 * In this case default item is the last one in the array.
 */

public class NonLastItemSpinnerAdapter extends ArrayAdapter<String> {

    public NonLastItemSpinnerAdapter(Context context, String[] items, int theLayoutResId) {
        super(context, theLayoutResId, items);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
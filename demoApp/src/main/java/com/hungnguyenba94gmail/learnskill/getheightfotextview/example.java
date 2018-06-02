package com.hungnguyenba94gmail.learnskill.getheightfotextview;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hungnguyenba94gmail.learnskill.R;

public class example {
    private Context context;
    private int widthMeasureSpec;
    private int heightMeasureSpec;
    private int heightOfEachLine;
    private int paddingFirstLine;

    private void calculateHeightOfEachLine() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int deviceWidth = size.x;
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        //1 line = 76; 2 lines = 76 + 66; 3 lines = 76 + 66 + 66
        //=> height of first line = 76 pixel; height of second line = third line =... n line = 66 pixel
        int heightOfFirstLine = getHeightOfTextView("A");
        int heightOfSecondLine = getHeightOfTextView("A\nA") - heightOfFirstLine;
        paddingFirstLine = heightOfFirstLine - heightOfSecondLine;
        heightOfEachLine = heightOfSecondLine;
    }

    private int getHeightOfTextView(String text) {
        // Getting height of text view before rendering to layout
        TextView textView = new TextView(context);
        textView.setPadding(10, 0, 10, 0);
        //textView.setTypeface(typeface);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    private int getLineCountOfTextViewBeforeRendering(String text) {
        return (getHeightOfTextView(text) - paddingFirstLine) / heightOfEachLine;
    }
}
//Note: This code also must be set for real textview on screen
//textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.tv_size_14sp));

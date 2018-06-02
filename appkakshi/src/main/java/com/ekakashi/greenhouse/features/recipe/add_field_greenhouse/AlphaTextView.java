package com.ekakashi.greenhouse.features.recipe.add_field_greenhouse;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ptloc on 2/12/2018.
 */

public class AlphaTextView extends android.support.v7.widget.AppCompatTextView {

    public AlphaTextView(Context context) {
        super(context);
    }

    public AlphaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        setTextColor(getTextColors().withAlpha(alpha));
        setHintTextColor(getHintTextColors().withAlpha(alpha));
        setLinkTextColor(getLinkTextColors().withAlpha(alpha));
        getBackground().setAlpha(alpha);
        return true;
    }

    public void onDefaultTextView() {
        setTextColor(getTextColors().withAlpha(255));
        setHintTextColor(getHintTextColors().withAlpha(255));
        setLinkTextColor(getLinkTextColors().withAlpha(255));
        getBackground().setAlpha(255);
    }
}

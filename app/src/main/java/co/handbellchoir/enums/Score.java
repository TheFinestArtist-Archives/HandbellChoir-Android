package co.handbellchoir.enums;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import co.handbellchoir.R;

/**
 * Created by Leonardo on 11/8/15.
 */
public enum Score {

    PERFECT(R.color.red, R.drawable.gradient_red),
    VERY_GOOD(R.color.deep_orange, R.drawable.gradient_deep_orange),
    GOOD(R.color.orange, R.drawable.gradient_orange),
    NICE(R.color.amber, R.drawable.gradient_amber),
    WHAT(R.color.yellow, R.drawable.gradient_yellow);

    private int color;
    private int gradient;

    Score(@ColorRes int color, @DrawableRes int gradient) {
        this.color = color;
        this.gradient = gradient;
    }

    public int getColor() {
        return color;
    }

    public int getGradient() {
        return gradient;
    }
}

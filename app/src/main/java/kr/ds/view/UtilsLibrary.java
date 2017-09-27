package kr.ds.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import static kr.ds.karaokesongphp.R.*;

/**
 * Created by Administrator on 2016-11-09.
 */
public class UtilsLibrary {

    public UtilsLibrary() {
    }

    static int dpToPixels(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)((float)dp * scale + 0.5F);
    }

    static Drawable createButtonBackgroundDrawable(@NonNull Context context, int fillColor) {
        int buttonCornerRadius = dpToPixels(context, 2);
        if(Build.VERSION.SDK_INT >= 21) {
            TypedValue v = new TypedValue();
            boolean hasAttribute = context.getTheme().resolveAttribute(attr.colorControlHighlight, v, true);
            int rippleColor = hasAttribute?v.data: Color.parseColor("#88CCCCCC");
            return createButtonBackgroundDrawableLollipop(fillColor, rippleColor, buttonCornerRadius);
        } else {
            return createButtonBackgroundDrawableBase(fillColor, buttonCornerRadius);
        }
    }

    @TargetApi(21)
    private static Drawable createButtonBackgroundDrawableLollipop(int fillColor, int rippleColor, int cornerRadius) {
        Drawable d = createButtonBackgroundDrawableBase(fillColor, cornerRadius);
        return new RippleDrawable(ColorStateList.valueOf(rippleColor), d, (Drawable)null);
    }

    private static Drawable createButtonBackgroundDrawableBase(int color, int cornerRadius) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(0);
        d.setCornerRadius((float)cornerRadius);
        d.setColor(color);
        return d;
    }
}

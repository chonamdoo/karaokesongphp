package kr.ds.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ds.karaokesongphp.R;


/**
 * Created by Administrator on 2016-11-09.
 */
public class DsBottomDialog {
    protected final DsBottomDialog.Builder mBuilder;

    public final DsBottomDialog.Builder getBuilder() {
        return this.mBuilder;
    }

    protected DsBottomDialog(DsBottomDialog.Builder builder) {
        this.mBuilder = builder;
        this.mBuilder.bottomDialog = this.initDsBottomDialog(builder);
    }

    @UiThread
    public void show() {
        if (this.mBuilder != null && this.mBuilder.bottomDialog != null) {
            this.mBuilder.bottomDialog.show();
        }

    }

    @UiThread
    public void dismiss() {
        if (this.mBuilder != null && this.mBuilder.bottomDialog != null) {
            this.mBuilder.bottomDialog.dismiss();
        }

    }

    @UiThread
    private Dialog initDsBottomDialog(final DsBottomDialog.Builder builder) {
        final Dialog bottomDialog = new Dialog(builder.context, R.style.BottomDialogs);
        View view = builder.activity.getLayoutInflater().inflate(R.layout.bottom_dialog, (ViewGroup) null);
        ImageView vIcon = (ImageView) view.findViewById(R.id.bottomDialog_icon);
        TextView vTitle = (TextView) view.findViewById(R.id.bottomDialog_title);
        TextView vContent = (TextView) view.findViewById(R.id.bottomDialog_content);
        FrameLayout vCustomView = (FrameLayout) view.findViewById(R.id.bottomDialog_custom_view);
        Button vNegative = (Button) view.findViewById(R.id.bottomDialog_cancel);
        Button vPositive = (Button) view.findViewById(R.id.bottomDialog_ok);

        if (builder.icon != null) {
            vIcon.setVisibility(View.VISIBLE);
            vIcon.setImageDrawable(builder.icon);

            vIcon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    bottomDialog.dismiss();
                }
            });
        }

        if (builder.title != null) {
            vTitle.setText(builder.title);
            vTitle.setVisibility(View.VISIBLE);
        }else{
            vTitle.setVisibility(View.GONE);
        }

        if (builder.content != null) {
            vContent.setText(builder.content);
            vContent.setVisibility(View.VISIBLE);
        }else{
            vContent.setVisibility(View.GONE);
        }

        if (builder.customView != null) {
            if (builder.customView.getParent() != null) {
                ((ViewGroup) builder.customView.getParent()).removeAllViews();
            }

            vCustomView.addView(builder.customView);
            vCustomView.setPadding(builder.customViewPaddingLeft, builder.customViewPaddingTop, builder.customViewPaddingRight, builder.customViewPaddingBottom);
        }

        if (builder.btn_positive != null) {
            vPositive.setVisibility(View.VISIBLE);
            vPositive.setText(builder.btn_positive);
            vPositive.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (builder.btn_positive_callback != null) {
                        builder.btn_positive_callback.onClick(DsBottomDialog.this);
                    }

                    if (builder.isAutoDismiss) {
                        bottomDialog.dismiss();
                    }

                }
            });
            if (builder.btn_colorPositive != 0) {
                vPositive.setTextColor(builder.btn_colorPositive);
            }

            if (builder.btn_colorPositiveBackground == 0) {
                TypedValue buttonBackground = new TypedValue();
                boolean hasColorPrimary = builder.context.getTheme().resolveAttribute(R.attr.colorPrimary, buttonBackground, true);
                builder.btn_colorPositiveBackground = !hasColorPrimary ? buttonBackground.data : ContextCompat.getColor(builder.context, R.color.colorPrimary);
            }

            Drawable buttonBackground1 = UtilsLibrary.createButtonBackgroundDrawable(builder.activity, builder.btn_colorPositiveBackground);
            if (Build.VERSION.SDK_INT >= 16) {
                vPositive.setBackground(buttonBackground1);
            } else {
                vPositive.setBackgroundDrawable(buttonBackground1);
            }
        }

        if (builder.btn_negative != null) {
            vNegative.setVisibility(View.VISIBLE);
            vNegative.setText(builder.btn_negative);
            vNegative.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (builder.btn_negative_callback != null) {
                        builder.btn_negative_callback.onClick(DsBottomDialog.this);
                    }

                    if (builder.isAutoDismiss) {
                        bottomDialog.dismiss();
                    }

                }
            });
            if (builder.btn_colorNegative != 0) {
                vNegative.setTextColor(builder.btn_colorNegative);
            }
        }

        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(builder.isCancelable);
        bottomDialog.getWindow().setLayout(-1, -2);
        bottomDialog.getWindow().setGravity(80);
        return bottomDialog;
    }

    public interface ButtonCallback {
        void onClick(@NonNull DsBottomDialog var1);
    }

    public static class Builder {
        protected Activity activity;
        protected Context context;
        protected Dialog bottomDialog;
        protected Drawable icon;
        protected CharSequence title;
        protected CharSequence content;
        protected CharSequence btn_negative;
        protected CharSequence btn_positive;
        protected DsBottomDialog.ButtonCallback btn_negative_callback;
        protected DsBottomDialog.ButtonCallback btn_positive_callback;
        protected boolean isAutoDismiss;
        protected int btn_colorNegative;
        protected int btn_colorPositive;
        protected int btn_colorPositiveBackground;
        protected View customView;
        protected int customViewPaddingLeft;
        protected int customViewPaddingTop;
        protected int customViewPaddingRight;
        protected int customViewPaddingBottom;
        protected boolean isCancelable;

        public Builder(@NonNull Context context) {
            this.activity = (Activity) context;
            this.context = context;
            this.isCancelable = true;
            this.isAutoDismiss = true;
        }

        public DsBottomDialog.Builder setTitle(@StringRes int titleRes) {
            this.setTitle(this.context.getString(titleRes));
            return this;
        }

        public DsBottomDialog.Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public DsBottomDialog.Builder setContent(@StringRes int contentRes) {
            this.setContent(this.context.getString(contentRes));
            return this;
        }

        public DsBottomDialog.Builder setContent(@NonNull CharSequence content) {
            this.content = content;
            return this;
        }

        public DsBottomDialog.Builder setIcon(@NonNull Drawable icon) {
            this.icon = icon;
            return this;
        }

        public DsBottomDialog.Builder setIcon(@DrawableRes int iconRes) {
            this.icon = ResourcesCompat.getDrawable(this.context.getResources(), iconRes, (Resources.Theme) null);
            return this;
        }

        public DsBottomDialog.Builder setPositiveBackgroundColorResource(@ColorRes int buttonColorRes) {
            this.btn_colorPositiveBackground = ResourcesCompat.getColor(this.context.getResources(), buttonColorRes, (Resources.Theme) null);
            return this;
        }

        public DsBottomDialog.Builder setPositiveBackgroundColor(int color) {
            this.btn_colorPositiveBackground = color;
            return this;
        }

        public DsBottomDialog.Builder setPositiveTextColorResource(@ColorRes int textColorRes) {
            this.btn_colorPositive = ResourcesCompat.getColor(this.context.getResources(), textColorRes, (Resources.Theme) null);
            return this;
        }

        public DsBottomDialog.Builder setPositiveTextColor(int color) {
            this.btn_colorPositive = color;
            return this;
        }

        public DsBottomDialog.Builder setPositiveText(@StringRes int buttonTextRes) {
            this.setPositiveText(this.context.getString(buttonTextRes));
            return this;
        }

        public DsBottomDialog.Builder setPositiveText(@NonNull CharSequence buttonText) {
            this.btn_positive = buttonText;
            return this;
        }

        public DsBottomDialog.Builder onPositive(@NonNull DsBottomDialog.ButtonCallback buttonCallback) {
            this.btn_positive_callback = buttonCallback;
            return this;
        }

        public DsBottomDialog.Builder setNegativeTextColorResource(@ColorRes int textColorRes) {
            this.btn_colorNegative = ResourcesCompat.getColor(this.context.getResources(), textColorRes, (Resources.Theme) null);
            return this;
        }

        public DsBottomDialog.Builder setNegativeTextColor(int color) {
            this.btn_colorNegative = color;
            return this;
        }

        public DsBottomDialog.Builder setNegativeText(@StringRes int buttonTextRes) {
            this.setNegativeText(this.context.getString(buttonTextRes));
            return this;
        }

        public DsBottomDialog.Builder setNegativeText(@NonNull CharSequence buttonText) {
            this.btn_negative = buttonText;
            return this;
        }

        public DsBottomDialog.Builder onNegative(@NonNull DsBottomDialog.ButtonCallback buttonCallback) {
            this.btn_negative_callback = buttonCallback;
            return this;
        }

        public DsBottomDialog.Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public DsBottomDialog.Builder autoDismiss(boolean autodismiss) {
            this.isAutoDismiss = autodismiss;
            return this;
        }

        public DsBottomDialog.Builder setCustomView(View customView) {
            this.customView = customView;
            this.customViewPaddingLeft = 0;
            this.customViewPaddingRight = 0;
            this.customViewPaddingTop = 0;
            this.customViewPaddingBottom = 0;
            return this;
        }

        public DsBottomDialog.Builder setCustomView(View customView, int left, int top, int right, int bottom) {
            this.customView = customView;
            this.customViewPaddingLeft = UtilsLibrary.dpToPixels(this.context, left);
            this.customViewPaddingRight = UtilsLibrary.dpToPixels(this.context, right);
            this.customViewPaddingTop = UtilsLibrary.dpToPixels(this.context, top);
            this.customViewPaddingBottom = UtilsLibrary.dpToPixels(this.context, bottom);
            return this;
        }

        @UiThread
        public DsBottomDialog build() {
            return new DsBottomDialog(this);
        }

        @UiThread
        public DsBottomDialog show() {
            DsBottomDialog bottomDialog = this.build();
            bottomDialog.show();
            return bottomDialog;
        }


    }
}
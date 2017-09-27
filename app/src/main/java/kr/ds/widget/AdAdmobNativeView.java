package kr.ds.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import kr.ds.karaokesongphp.R;

/**
 * Created by Administrator on 2017-08-02.
 */

public class AdAdmobNativeView extends View {
    private Context mContext;
    private View mAdmobView;
    private NativeExpressAdView mNativeExpressAdView;
    private LinearLayout mContainer;

    public interface ResultListener {
        public <T> void OnLoad();
        public <T> void OnFail();
    }
    private ResultListener mResultListener;
    public AdAdmobNativeView setCallBack(ResultListener resultListener){
        mResultListener = resultListener;
        return this;
    }
    public AdAdmobNativeView(Context context) {
        super(context);
        mContext = context;
    }
    public AdAdmobNativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public AdAdmobNativeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }
    public AdAdmobNativeView setContainer(View v){
        mContainer = (LinearLayout) v;
        return this;
    }
    public AdAdmobNativeView setLayout(){
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdmobView = inflater.inflate(R.layout.native_admob, null);
        mContainer.addView(mAdmobView);
        mNativeExpressAdView = (NativeExpressAdView) mAdmobView.findViewById(R.id.adView);
        mNativeExpressAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(mResultListener != null){
                    mResultListener.OnLoad();
                }

            }
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(mResultListener != null){
                    mResultListener.OnFail();
                }
            }
        });
        mNativeExpressAdView.loadAd(new AdRequest.Builder().build());

        return this;
    }
}

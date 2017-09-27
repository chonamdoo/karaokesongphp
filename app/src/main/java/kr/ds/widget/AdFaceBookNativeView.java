package kr.ds.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import kr.ds.karaokesongphp.MainActivity;
import kr.ds.karaokesongphp.R;

import static kr.ds.karaokesongphp.R.id.adView;

/**
 * Created by Administrator on 2017-08-02.
 */

public class AdFaceBookNativeView extends View {
    private Context mContext;
    private View mFaceBookView;
    private NativeAd nativeAd;
    private LinearLayout mContainer;



    public interface ResultListener {
        public <T> void OnLoad();
        public <T> void OnFail();
    }
    private ResultListener mResultListener;
    public AdFaceBookNativeView setCallBack(ResultListener resultListener){
        mResultListener = resultListener;
        return this;
    }
    public AdFaceBookNativeView(Context context) {
        super(context);
        mContext = context;
    }
    public AdFaceBookNativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public AdFaceBookNativeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }
    public AdFaceBookNativeView setContainer(View v){
        mContainer = (LinearLayout) v;
        return this;
    }

    public AdFaceBookNativeView setLayout(final int resource){
        nativeAd = new NativeAd(mContext, "1978931125697459_1978931292364109");
        nativeAd.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                super.onError(ad, adError);

                if(mResultListener != null){
                    mResultListener.OnFail();
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mFaceBookView = inflater.inflate(resource, null);
                mContainer.addView(mFaceBookView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = (ImageView) mFaceBookView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView) mFaceBookView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = (MediaView) mFaceBookView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView) mFaceBookView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = (TextView) mFaceBookView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = (Button) mFaceBookView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
                nativeAdMedia.setNativeAd(nativeAd);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) mFaceBookView.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(mContext, nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(mContainer, clickableViews);

                if(mResultListener != null){
                    mResultListener.OnLoad();
                }

            }

            @Override
            public void onAdClicked(Ad ad) {
                super.onAdClicked(ad);
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                super.onInterstitialDisplayed(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                super.onLoggingImpression(ad);
            }
        });
        nativeAd.loadAd();
        return this;
    }
}

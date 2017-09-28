package kr.ds.karaokesongphp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import kr.ds.utils.SharedPreference;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Administrator on 2016-11-08.
 */
public class IntroActivity extends BaseActivity{
    private InterstitialAd interstitialAd;
    private CountDownTimer mStartCountDownTimer;
    private boolean interstitialCanceled = false;
    private int mCount = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        ShortcutBadger.removeCount(getApplicationContext());
        SharedPreference.putSharedPreference(getApplicationContext(), "badger_count", 0);

        //OnNext();
        setAdMob();

    }
    public void setAdMob(){
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId("ca-app-pub-3957553723261794/3514109656");
        AdRequest adRequest = new AdRequest.Builder().build();

        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // TODO Auto-generated method stub
                super.onAdClosed();
                OnNext();
            }
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();

            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO Auto-generated method stub
                super.onAdFailedToLoad(errorCode);
                OnNext();
            }
        });

        //10초 딜레이
        mStartCountDownTimer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                if(mCount > 1){
                    if (interstitialAd.isLoaded()) {
                        if(mStartCountDownTimer != null){
                            mStartCountDownTimer.cancel();
                        }
                        interstitialCanceled = true;
                        interstitialAd.show();
                    }
                }
                mCount++;
            }
            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                if(!interstitialCanceled) {
                    OnNext();
                }
            }
        };
        mStartCountDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mStartCountDownTimer != null){
            mStartCountDownTimer.cancel();
        }
    }

    public void OnNext(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
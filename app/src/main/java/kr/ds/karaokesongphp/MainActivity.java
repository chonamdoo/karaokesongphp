package kr.ds.karaokesongphp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Random;

import kr.ds.config.Config;
import kr.ds.fragment.BaseFragment;
import kr.ds.fragment.BookMarkFragment;
import kr.ds.fragment.ChannelFragment;
import kr.ds.fragment.List1Fragment;
import kr.ds.fragment.MainFragment;
import kr.ds.fragment.RecordFragment;
import kr.ds.fragment.SearchFragment;
import kr.ds.fragment.SettingFragment;
import kr.ds.store.MainStoreTypeDialog;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;

public class MainActivity extends MainBaseActivity implements View.OnClickListener{

    private FragmentManager mFm;
    private FragmentTransaction mFt;

    private BaseFragment mFragment1 = null;
    private BaseFragment mFragment2 = null;
    private BaseFragment mFragment3 = null;
    private BaseFragment mFragment4 = null;
    private BaseFragment mFragment5 = null;
    private BaseFragment mFragment6 = null;


    private Toolbar mToolbar;
    private NativeAd mNativeAd;
    private LinearLayout mAdView;
    private Boolean isNativeCheck = false;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private LinearLayout mLinearLayoutTab1,mLinearLayoutTab2,mLinearLayoutTab3,mLinearLayoutTab4,mLinearLayoutTab5, mLinearLayoutTab6;
    private ImageView mImageViewTab1,mImageViewTab2,mImageViewTab3,mImageViewTab4,mImageViewTab5,mImageViewTab6;

    private final int TAB1 = 1;
    private final int TAB2 = 2;
    private final int TAB3 = 3;
    private final int TAB4 = 4;
    private final int TAB5 = 5;
    private final int TAB6 = 6;

    private com.facebook.ads.InterstitialAd interstitialAdFackBook;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);


        (mLinearLayoutTab1 = (LinearLayout)findViewById(R.id.linearLayout_tab1)).setOnClickListener(this);
        (mLinearLayoutTab2 = (LinearLayout)findViewById(R.id.linearLayout_tab2)).setOnClickListener(this);
        (mLinearLayoutTab3 = (LinearLayout)findViewById(R.id.linearLayout_tab3)).setOnClickListener(this);
        (mLinearLayoutTab4 = (LinearLayout)findViewById(R.id.linearLayout_tab4)).setOnClickListener(this);
        (mLinearLayoutTab5 = (LinearLayout)findViewById(R.id.linearLayout_tab5)).setOnClickListener(this);
        (mLinearLayoutTab6 = (LinearLayout)findViewById(R.id.linearLayout_tab6)).setOnClickListener(this);

        mImageViewTab1 = (ImageView) findViewById(R.id.imageView_tab1);
        mImageViewTab2 = (ImageView) findViewById(R.id.imageView_tab2);
        mImageViewTab3 = (ImageView) findViewById(R.id.imageView_tab3);
        mImageViewTab4 = (ImageView) findViewById(R.id.imageView_tab4);
        mImageViewTab5 = (ImageView) findViewById(R.id.imageView_tab5);
        mImageViewTab6 = (ImageView) findViewById(R.id.imageView_tab6);

        setTab(TAB1);

        if (checkPlayServices() && DsObjectUtils.getInstance(getApplicationContext()).isEmpty(SharedPreference.getSharedPreference(getApplicationContext(), Config.TOKEN))) { //토큰이 없는경우..
            Intent intent = new Intent(getApplicationContext(), RegistrationIntentService.class);
            startService(intent);
        }

//        if(isFaceBookCheck()){
//            setFaceBook();
//        }
    }

    private void setFaceBook() {

        interstitialAdFackBook = new com.facebook.ads.InterstitialAd(getApplicationContext(), "1728884537412489_1728884787412464");
        interstitialAdFackBook.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                super.onError(ad, adError);
                Log.i("TEST","error");
                Log.i("TEST",adError.toString());

            }
            @Override
            public void onAdLoaded(Ad ad) {
                super.onAdLoaded(ad);
                int random = new Random().nextInt(3);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interstitialAdFackBook.show();
                    }
                }, random*1000);
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
        interstitialAdFackBook.loadAd();
    }



    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }


    private void setHideFragment(FragmentTransaction transaction) {
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }
        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }
        if (mFragment4 != null) {
            transaction.hide(mFragment4);
        }
        if (mFragment5 != null) {
            transaction.hide(mFragment5);
        }
        if (mFragment6 != null) {
            transaction.hide(mFragment6);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void setTab(int tab){
        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        setHideFragment(mFt);

        if(mFragment6 != null){
            mFragment6.Tab(tab);
        }
        if(tab == 5){
            if(mFragment5 !=  null) {
                mFragment5.Tab(tab);
            }
        }

        mImageViewTab1.setBackgroundResource(R.drawable.tab1_off);
        mImageViewTab2.setBackgroundResource(R.drawable.tab2_off);
        mImageViewTab3.setBackgroundResource(R.drawable.tab3_off);
        mImageViewTab4.setBackgroundResource(R.drawable.tab4_off);
        mImageViewTab5.setBackgroundResource(R.drawable.tab5_off);
        mImageViewTab6.setBackgroundResource(R.drawable.tab6_off);

        if(tab == TAB1){
            mImageViewTab1.setBackgroundResource(R.drawable.tab1_on);
            if (mFragment1 == null) {
                mFragment1 = BaseFragment.newInstance(MainFragment.class);
                mFt.add(R.id.content_frame, mFragment1);
            } else {
                mFt.show(mFragment1);
            }
        }else if(tab == TAB2){
            mImageViewTab2.setBackgroundResource(R.drawable.tab2_on);
            if (mFragment2 == null) {
                mFragment2 = BaseFragment.newInstance(List1Fragment.class);
                mFt.add(R.id.content_frame, mFragment2);
            } else {
                mFt.show(mFragment2);
            }
        }else if(tab == TAB3){
            mImageViewTab3.setBackgroundResource(R.drawable.tab3_on);
            if (mFragment3 == null) {
                mFragment3 = BaseFragment.newInstance(SearchFragment.class);
                mFt.add(R.id.content_frame, mFragment3);
            } else {
                mFt.show(mFragment3);
            }
        }else if(tab == TAB4){
            mImageViewTab4.setBackgroundResource(R.drawable.tab4_on);
            if (mFragment4 == null) {
                mFragment4 = BaseFragment.newInstance(BookMarkFragment.class);
                mFt.add(R.id.content_frame, mFragment4);
            } else {
                mFt.show(mFragment4);
            }
        }else if(tab == TAB5){
            mImageViewTab5.setBackgroundResource(R.drawable.tab5_on);
            if (mFragment5 == null) {
                mFragment5 = BaseFragment.newInstance(SettingFragment.class);
                mFt.add(R.id.content_frame, mFragment5);
            } else {
                mFt.show(mFragment5);
            }

        }else if(tab == TAB6){
            mImageViewTab6.setBackgroundResource(R.drawable.tab6_on);
            if (mFragment6 == null) {
                mFragment6 = BaseFragment.newInstance(RecordFragment.class);
                mFt.add(R.id.content_frame, mFragment6);
            } else {
                mFt.show(mFragment6);
            }

        }
        mFt.commitAllowingStateLoss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(fragment != null){
            if(fragment instanceof BookMarkFragment){
                ((BookMarkFragment) fragment).onActivityResult(requestCode, resultCode, data);
            }else if(fragment instanceof List1Fragment){
                ((List1Fragment) fragment).onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearLayout_tab1:
                setTab(TAB1);
                break;
            case R.id.linearLayout_tab2:
                setTab(TAB2);
                break;
            case R.id.linearLayout_tab3:
                setTab(TAB3);
                break;
            case R.id.linearLayout_tab4:
                setTab(TAB4);
                break;
            case R.id.linearLayout_tab5:
                setTab(TAB5);
                break;
            case R.id.linearLayout_tab6:
                setTab(TAB6);
                break;
        }
    }
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (KeyCode == KeyEvent.KEYCODE_BACK) {
                final MainStoreTypeDialog mMainDialog = new MainStoreTypeDialog();// call the static method
                mMainDialog.show(getSupportFragmentManager(), "dialog");
                return true;
            }
        }
        return super.onKeyDown(KeyCode, event);
    }
}
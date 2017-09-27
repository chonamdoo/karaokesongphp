package kr.ds.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import kr.ds.utils.ScreenUtils;

/**
 * Created by Administrator on 2016-03-28.
 */
@SuppressLint("NewApi")
public class ContentViewPager extends LinearLayout{
    private ViewPager mViewPager;
    private ContentViewPagerAdapter mContentViewPagerAdapter;
    private Context mContext;
    private ScreenUtils mScreenUtils = ScreenUtils.getInstacne();
    private String[] mData;

    private int i = 0;
    private Handler mHandler;
    private Runnable mRunnable;

    public ContentViewPager(Context context) {
        super(context);
        mContext = context;
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ContentViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setView(String[] data){
        mData   = data;
        mViewPager = new ViewPager(mContext);
        //mViewPager.setPadding(mScreenUtils.getPixelFromDPI(mContext, 7),0,mScreenUtils.getPixelFromDPI(mContext, 7),0);
        mViewPager.setClipToPadding(false);
        //mViewPager.setPageMargin(mScreenUtils.getPixelFromDPI(mContext, 4));
        mContentViewPagerAdapter = new ContentViewPagerAdapter(mContext, mData);
        mViewPager.setAdapter(mContentViewPagerAdapter);
        addView(mViewPager);
        Scheduler();

    }

    public void Scheduler(){
        i = 0;
        mHandler = new Handler(Looper.getMainLooper());
        mRunnable = new Runnable() {
            @Override
            public void run() {
                i++;
                if(mData.length == i) {
                    i = 0;
                }
                mViewPager.setCurrentItem(i);
                //Log.i("TEST",i+"");
                mHandler.postDelayed(mRunnable, 4000);
            }
        };
        mHandler.postDelayed(mRunnable, 4000);
    }

    public void finish(){
        if(mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}

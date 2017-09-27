package kr.ds.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.util.Pools;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.ArrayList;

import kr.ds.karaokesongphp.R;
import kr.ds.utils.ScreenUtils;


/**
 * Created by Administrator on 2016-03-28.
 */
public class ContentViewPagerAdapter extends PagerAdapter {
    private String[] mData;
    private Context mContext;
    public ContentViewPagerAdapter(Context context, String[] data){
        mContext = context;
        mData = data;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_viewpager_item, null);
        ImageView ivImage = (ImageView) view.findViewById(R.id.imageView);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Glide.with(mContext)
                .load(mData[position])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImage);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout rl = (LinearLayout) object;
        if (rl != null) {
            ImageView ivImage = (ImageView) rl.findViewById(R.id.imageView);
        }
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
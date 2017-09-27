package kr.ds.dev;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import kr.ds.config.Config;
import kr.ds.data.BaseResultListener;
import kr.ds.karaokesongphp.R;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.VersionUtils;

/**
 * Created by Administrator on 2017-02-07.
 */
public class DevBannerView extends LinearLayout {
    private View mView;
    private ArrayList<DevHandler> mData;

    private ImageView mImageView;
    private TextView mTextViewName;
    private TextView mTextViewSubName;
    private LinearLayout mLinearLayoutBg;

    public DevBannerView(Context context) {
        super(context);
        init(context);
    }

    public DevBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DevBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public DevBannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(final Context context){
        if(Config.TYPE == Config.MARKET) {
            LayoutInflater inflater = LayoutInflater.from(context);
            mView = (View) inflater.inflate(R.layout.dev_banner_view, null);

            (mLinearLayoutBg = (LinearLayout) mView.findViewById(R.id.linearLayout_bg)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    new DevLogData().clear().setCallBack(new BaseResultListener() {
                        @Override
                        public <T> void OnComplete() {

                        }

                        @Override
                        public <T> void OnComplete(Object data) {
                            if (data != null) {
                                try {
                                    Uri uri = Uri.parse("market://details?id=" + mData.get(0).getLink());
                                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                    context.startActivity(it);
                                } catch (Exception e) {
                                    Toast.makeText(context, "오류가 발생되었습니다. 문제가 계속 발생시 관리자에게 문의 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void OnMessage(String str) {

                        }
                    }).setUrl("http://parsingds.cafe24.com/app/dev/log.php?package=" + mData.get(0).getLink() + "&url_packpage=" + VersionUtils.VersionPackageName(context)).setParam("").getView();

                }
            });

            mTextViewName = (TextView) mView.findViewById(R.id.textView);
            mTextViewSubName = (TextView) mView.findViewById(R.id.textView_sub);
            mImageView = (ImageView) mView.findViewById(R.id.imageView);

            new DevData().clear().setCallBack(new BaseResultListener() {
                @Override
                public <T> void OnComplete() {

                }

                @Override
                public <T> void OnComplete(Object data) {
                    if (data != null) {
                        mData = (ArrayList<DevHandler>) data;

                        if (!DsObjectUtils.isEmpty(mData.get(0).getImage())) {
                            Glide.with(context)
                                    .load(mData.get(0).getImage())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(mImageView);
                        } else {
                            mImageView.setVisibility(View.GONE);
                        }
                        if (!DsObjectUtils.isEmpty(mData.get(0).getName())) {
                            mTextViewName.setVisibility(View.VISIBLE);
                            mTextViewName.setText(mData.get(0).getName());
                        } else {
                            mTextViewName.setVisibility(View.GONE);
                            mTextViewName.setText("");
                        }
                        if (!DsObjectUtils.isEmpty(mData.get(0).getSub_name())) {
                            mTextViewSubName.setVisibility(View.VISIBLE);
                            mTextViewSubName.setText(mData.get(0).getSub_name());
                        } else {
                            mTextViewSubName.setVisibility(View.GONE);
                            mTextViewSubName.setText("");
                        }
                        addView(mView);
                    }
                }
                @Override
                public void OnMessage(String str) {

                }
            }).setUrl("http://parsingds.cafe24.com/app/dev/banner.php?package=" + VersionUtils.VersionPackageName(context)).setParam("").getView();
        }

    }

}
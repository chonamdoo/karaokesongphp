package kr.ds.dev;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ds.config.Config;
import kr.ds.data.BaseResultListener;
import kr.ds.karaokesongphp.R;
import kr.ds.utils.VersionUtils;
import kr.ds.widget.ScrollListView;

/**
 * Created by Administrator on 2017-02-07.
 */
public class DevView extends LinearLayout {
    private View mView;
    private DevAdapter mDevAdapter;
    private ScrollListView mScrollListView;
    private ArrayList<DevHandler> mData;
    public DevView(Context context) {
        super(context);
        init(context);
    }

    public DevView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DevView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public DevView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(final Context context){
        if(Config.TYPE == Config.MARKET) {
            LayoutInflater inflater = LayoutInflater.from(context);
            mView = (View) inflater.inflate(R.layout.dev_view, null);
            mScrollListView = (ScrollListView) mView.findViewById(R.id.listView);
            mScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    new DevLogData().clear().setCallBack(new BaseResultListener() {
                        @Override
                        public <T> void OnComplete() {

                        }

                        @Override
                        public <T> void OnComplete(Object data) {
                            if (data != null) {
                                try {
                                    Uri uri = Uri.parse("market://details?id=" + mData.get(position).getLink());
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
                    }).setUrl("http://parsingds.cafe24.com/app/dev/log.php?package=" + mData.get(position).getLink() + "&url_packpage=" + VersionUtils.VersionPackageName(context)).setParam("").getView();
                }
            });

            new DevData().clear().setCallBack(new BaseResultListener() {
                @Override
                public <T> void OnComplete() {

                }

                @Override
                public <T> void OnComplete(Object data) {
                    if (data != null) {
                        mData = (ArrayList<DevHandler>) data;
                        mDevAdapter = new DevAdapter(context, (ArrayList<DevHandler>) data);
                        mScrollListView.setAdapter(mDevAdapter);
                        addView(mView);
                    }
                }

                @Override
                public void OnMessage(String str) {

                }
            }).setUrl("http://parsingds.cafe24.com/app/dev/list.php?package=" + VersionUtils.VersionPackageName(context)).setParam("").getView();
        }
    }
}

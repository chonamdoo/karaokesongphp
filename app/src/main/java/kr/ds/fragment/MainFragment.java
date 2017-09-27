package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kr.ds.adapter.MainItem1Adapter;
import kr.ds.adapter.MainItem2Adapter;
import kr.ds.config.Config;
import kr.ds.data.BaseResultListener;
import kr.ds.data.ChannelData;
import kr.ds.data.ListData;
import kr.ds.handler.ChannelHandler;
import kr.ds.handler.ListHandler;
import kr.ds.karaokesongphp.R;
import kr.ds.widget.AdAdmobNativeView;
import kr.ds.widget.AdFaceBookNativeView;

/**
 * Created by Administrator on 2017-07-21.
 */

public class MainFragment extends BaseFragment {
    private View mView;
    private Context mContext;

    private RecyclerView.LayoutManager mLayoutManager1, mLayoutManager2, mLayoutManager3, mLayoutManager4;
    private MainItem1Adapter mMainItem1Adapter, mMainItem3Adapter, mMainItem4Adapter;
    private MainItem2Adapter mMainItem2Adapter;//테마

    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private RecyclerView mRecyclerView4;

    private FrameLayout mFrameLayoutBg;
    private NestedScrollView mNestedScrollView;

    private LinearLayout mLinearLayoutNative;
    private LinearLayout mLinearLayoutNative2;
    private AdFaceBookNativeView mAdFaceBookNativeView;
    private AdAdmobNativeView mAdAdmobNativeView;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, null);
        mLinearLayoutNative = (LinearLayout) mView.findViewById(R.id.linearLayout_native);
        mLinearLayoutNative2 = (LinearLayout) mView.findViewById(R.id.linearLayout_native2);


        mAdFaceBookNativeView = new AdFaceBookNativeView(mContext);
        mAdFaceBookNativeView.setContainer(mLinearLayoutNative).setLayout(R.layout.native_facebook).setCallBack(new AdFaceBookNativeView.ResultListener() {
            @Override
            public <T> void OnLoad() {

            }
            @Override
            public <T> void OnFail() {
                if(mLinearLayoutNative.getChildCount() > 0) {
                    mLinearLayoutNative.removeAllViews();
                }
                mAdAdmobNativeView = new AdAdmobNativeView(mContext);
                mAdAdmobNativeView.setContainer(mLinearLayoutNative).setLayout().setCallBack(new AdAdmobNativeView.ResultListener() {
                    @Override
                    public <T> void OnLoad() {

                    }

                    @Override
                    public <T> void OnFail() {
                        mLinearLayoutNative.setVisibility(View.GONE);
                    }
                });
            }
        });

        mAdFaceBookNativeView = new AdFaceBookNativeView(mContext);
        mAdFaceBookNativeView.setContainer(mLinearLayoutNative2).setLayout(R.layout.native_facebook).setCallBack(new AdFaceBookNativeView.ResultListener() {
            @Override
            public <T> void OnLoad() {

            }
            @Override
            public <T> void OnFail() {
                if(mLinearLayoutNative2.getChildCount() > 0) {
                    mLinearLayoutNative2.removeAllViews();
                }
                mAdAdmobNativeView = new AdAdmobNativeView(mContext);
                mAdAdmobNativeView.setContainer(mLinearLayoutNative2).setLayout().setCallBack(new AdAdmobNativeView.ResultListener() {
                    @Override
                    public <T> void OnLoad() {

                    }

                    @Override
                    public <T> void OnFail() {
                        mLinearLayoutNative2.setVisibility(View.GONE);
                    }
                });
            }
        });




        mFrameLayoutBg = (FrameLayout) mView.findViewById(R.id.frameLayout_bg);
        mNestedScrollView = (NestedScrollView) mView.findViewById(R.id.scrollView);

        mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager3 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager4 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView1 = (RecyclerView)mView.findViewById(R.id.recycler_view1);
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(mLayoutManager1);

        mRecyclerView2 = (RecyclerView)mView.findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(mLayoutManager2);

        mRecyclerView3 = (RecyclerView)mView.findViewById(R.id.recycler_view3);
        mRecyclerView3.setHasFixedSize(true);
        mRecyclerView3.setLayoutManager(mLayoutManager3);

        mRecyclerView4 = (RecyclerView)mView.findViewById(R.id.recycler_view4);
        mRecyclerView4.setHasFixedSize(true);
        mRecyclerView4.setLayoutManager(mLayoutManager4);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrameLayoutBg.setVisibility(View.VISIBLE);
        mNestedScrollView.setVisibility(View.GONE);
        setMain1();
    }
    public void setMain1(){
        new ListData().clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {
            }
            @Override
            public <T> void OnComplete(Object data) {
                if(data != null){
                    ArrayList<ListHandler> mData = (ArrayList<ListHandler>) data;
                    mMainItem1Adapter = new MainItem1Adapter(mContext, mData);
                    mRecyclerView1.setAdapter(mMainItem1Adapter);
                    setMain3();
                }
            }
            @Override
            public void OnMessage(String str) {
            }
        }).setUrl(Config.URL+ Config.URL_XML+ Config.URL_MAIN1).setParam("").getView();
    }

    public void setMain2(){
        new ChannelData().clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {
            }
            @Override
            public <T> void OnComplete(Object data) {
                if(data != null){
                    ArrayList<ChannelHandler> mData = (ArrayList<ChannelHandler>) data;
                    mMainItem2Adapter = new MainItem2Adapter(mContext, mData);
                    mRecyclerView2.setAdapter(mMainItem2Adapter);
                    setMain3();
                }
            }
            @Override
            public void OnMessage(String str) {
            }
        }).setUrl(Config.URL+ Config.URL_XML+ Config.URL_MAIN2).setParam("").getView();
    }

    public void setMain3(){
        new ListData().clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {
            }
            @Override
            public <T> void OnComplete(Object data) {
                if(data != null){
                    ArrayList<ListHandler> mData = (ArrayList<ListHandler>) data;
                    mMainItem3Adapter = new MainItem1Adapter(mContext, mData);
                    mRecyclerView3.setAdapter(mMainItem3Adapter);
                    setMain4();
                }
            }
            @Override
            public void OnMessage(String str) {
            }
        }).setUrl(Config.URL+ Config.URL_XML+ Config.URL_MAIN3).setParam("").getView();
    }

    public void setMain4(){
        new ListData().clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {
            }
            @Override
            public <T> void OnComplete(Object data) {
                if(data != null){
                    ArrayList<ListHandler> mData = (ArrayList<ListHandler>) data;
                    mMainItem4Adapter = new MainItem1Adapter(mContext, mData);
                    mRecyclerView4.setAdapter(mMainItem4Adapter);

                    mFrameLayoutBg.setVisibility(View.GONE);
                    mNestedScrollView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void OnMessage(String str) {
            }
        }).setUrl(Config.URL+ Config.URL_XML+ Config.URL_MAIN4).setParam("").getView();
    }

    @Override
    public void Tab(int tab) {
    }
}

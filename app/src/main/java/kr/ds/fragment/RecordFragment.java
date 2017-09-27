package kr.ds.fragment;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.vision.text.Text;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import kr.ds.adapter.RecordAdapter;
import kr.ds.config.Config;
import kr.ds.data.BaseResultListener;
import kr.ds.data.RecordData;
import kr.ds.db.BookMarkDB;
import kr.ds.db.RecordDB;
import kr.ds.handler.RecordHandler;
import kr.ds.karaokesongphp.R;
import kr.ds.karaokesongphp.SubActivity;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;

import static android.os.Build.VERSION_CODES.M;
import static kr.ds.karaokesongphp.R.id.seekBar;
import static kr.ds.utils.SharedPreference.getBooleanSharedPreference;


/**
 * Created by Administrator on 2016-08-31.
 */
public class RecordFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<RecordHandler> mData = null;
    private ArrayList<RecordHandler> mMainData = null;
    private int mNumber = 10;
    private int mPage = 1;
    private int startPage = 0;
    private int endPage = 0;

    private View mView;
    private ProgressBar mProgressBar;
    private ListView mListView;
    private RecordData mRecordData;
    private RecordAdapter mRecordAdapter;
    private int mCurrentScrollState;
    private Boolean mIsTheLoding = false;
    private SwipeRefreshLayout mSwipeLayout;

    private final static int LIST = 0;
    private final static int ONLOAD = 1;
    private final static int REFRESH = 2;
    private Context mContext;

    private Button mButton;
    private MediaPlayer mMediaPlayer;
    private RecordDB mRecordDB;

    private TextView mTextViewTitle, mTextViewTime, mTextViewTime2;
    private ImageButton mImageButtonPause;
    private ImageButton mImageButtonStop;

    private TextView mTextViewTopName;

    private SeekBar mSeekBar;
    private Boolean isPlaying = false;

    class MyThread extends Thread {
        @Override
        public void run() {
            while(isPlaying && mMediaPlayer != null) {
                try{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mMediaPlayer != null) {
                                if (mMediaPlayer.isPlaying()) {
                                    setTimer(mMediaPlayer.getCurrentPosition());
                                }
                            }
                        }
                    });
                    if(mMediaPlayer != null) {
                        if (mMediaPlayer.isPlaying()) {
                            mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                        }
                    }
                }catch (Exception e){
                    if(mMyThread != null && mMyThread.isAlive()) {
                        mMyThread.interrupt();
                    }
                }

            }
        }
    }

    private MyThread mMyThread;
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mContext = getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMyThread = new MyThread();

        mView = inflater.inflate(R.layout.fragment_record_list, null);
        mSeekBar = (SeekBar)mView.findViewById(seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (mMediaPlayer != null) {
                        setTimer(progress);
                        mMediaPlayer.seekTo(progress);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTextViewTopName = (TextView)mView.findViewById(R.id.textView_top_name);
        mTextViewTime = (TextView) mView.findViewById(R.id.textView_time);
        mTextViewTime2 = (TextView) mView.findViewById(R.id.textView_time2);
        mTextViewTitle = (TextView) mView.findViewById(R.id.textView_title);
        mTextViewTitle.setSelected(true);
        (mImageButtonPause = (ImageButton)mView.findViewById(R.id.imagebutton_pause)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {
                        if(mMyThread != null && mMyThread.isAlive()) {
                            mMyThread.interrupt();
                        }
                        isPlaying = false;
                        mMediaPlayer.pause();
                        mImageButtonPause.setImageResource(R.drawable.btn_play);
                    }else{
                        mMediaPlayer.start();
                        mMyThread = new MyThread();
                        mMyThread.start();
                        isPlaying = true;
                        mImageButtonPause.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });
        (mImageButtonStop = (ImageButton)mView.findViewById(R.id.imagebutton_stop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReset();

            }
        });


        mListView = (ListView)mView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                try {
                    setReset();
                    if(!DsObjectUtils.isEmpty(mData.get(position).getTitle())){
                        mTextViewTitle.setText(mData.get(position).getTitle());
                    }
                    mImageButtonPause.setAlpha(1f);
                    mImageButtonStop.setAlpha(1f);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    FileInputStream fileInputStream = new FileInputStream(mData.get(position).getUrl_file());
                    FileDescriptor fileDescriptor = fileInputStream.getFD();
                    mMediaPlayer.setDataSource(fileDescriptor);
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            setTimer2(mp.getDuration());
                            mSeekBar.setMax(mp.getDuration());
                            mMediaPlayer.start();
                            mMyThread = new MyThread();
                            mMyThread.start();
                            isPlaying = true;

                        }
                    });
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            setReset();
                        }
                    });
                    mMediaPlayer.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mProgressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
        mSwipeLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary);
        return mView;
    }
    public void setLayoutReset(){
        mTextViewTitle.setText("");
        mTextViewTime.setText("00:00");
        mTextViewTime2.setText("00:00");
        mImageButtonPause.setAlpha(0.2f);
        mImageButtonStop.setAlpha(0.2f);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mProgressBar.setVisibility(View.VISIBLE);
        setList();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                mCurrentScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

                int topRowVerticalPosition = (mListView == null || mListView.getChildCount() == 0) ? 0 : mListView.getChildAt(0).getTop();
                if (firstVisibleItem == 0 && topRowVerticalPosition >= 0) {
                    mSwipeLayout.setEnabled(true);
                } else {
                    mSwipeLayout.setEnabled(false);
                }
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                if(!mIsTheLoding && loadMore &&  mCurrentScrollState != SCROLL_STATE_IDLE){
                    mIsTheLoding = true;
                    onLoadMore();
                }
            }
        });
    }

    public void setList(){

        new RecordData(mContext).clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {

            }
            @Override
            public <T> void OnComplete(Object data) {
                mProgressBar.setVisibility(View.GONE);
                mIsTheLoding = false;
                if(data != null){
                    mPage = 1;

                    mMainData = (ArrayList<RecordHandler>) data;
                    getTitle(String.valueOf(mMainData.size()));
                    if(mMainData.size() - ((mPage-1)*mNumber) > 0){
                        if(mMainData.size() >= mPage * mNumber){
                            startPage = (mPage-1) * mNumber;
                            endPage = mPage * mNumber;
                        }else{
                            startPage = (mPage-1) * mNumber;
                            endPage = mMainData.size();
                        }
                        mData  = new ArrayList<>();
                        for(int i=startPage; i< endPage; i++){
                            mData.add(mMainData.get(i));
                        }
                        mRecordAdapter = new RecordAdapter(mContext, mData);
                        AlphaInAnimationAdapter mAlphaInAnimationAdapter = new AlphaInAnimationAdapter(mRecordAdapter);
                        mAlphaInAnimationAdapter.setAbsListView(mListView);
                        mListView.setAdapter(mAlphaInAnimationAdapter);
                    }
                }else{
                    getTitle("0");
                    mListView.setAdapter(null);
                }
            }

            @Override
            public void OnMessage(String str) {

            }
        }).setUrl("").setParam("").getView();
    }

    public void setListRefresh(){
        new RecordData(mContext).clear().setCallBack(new BaseResultListener() {
            @Override
            public <T> void OnComplete() {

            }
            @Override
            public <T> void OnComplete(Object data) {
                mSwipeLayout.setRefreshing(false);
                mIsTheLoding = false;
                if(data != null){
                    mPage = 1;

                    mMainData = (ArrayList<RecordHandler>) data;
                    getTitle(String.valueOf(mMainData.size()));
                    if(mMainData.size() - ((mPage-1)*mNumber) > 0){
                        if(mMainData.size() >= mPage * mNumber){
                            startPage = (mPage-1) * mNumber;
                            endPage = mPage * mNumber;
                        }else{
                            startPage = (mPage-1) * mNumber;
                            endPage = mMainData.size();
                        }
                        mData  = new ArrayList<>();
                        for(int i=startPage; i< endPage; i++){
                            mData.add(mMainData.get(i));
                        }
                        mRecordAdapter = new RecordAdapter(mContext, mData);
                        AlphaInAnimationAdapter mAlphaInAnimationAdapter = new AlphaInAnimationAdapter(mRecordAdapter);
                        mAlphaInAnimationAdapter.setAbsListView(mListView);
                        mListView.setAdapter(mAlphaInAnimationAdapter);
                    }
                }else{
                    getTitle("0");
                    mListView.setAdapter(null);
                }
            }

            @Override
            public void OnMessage(String str) {

            }
        }).setUrl("").setParam("").getView();
    }

    public void setListOnLoad(){
        mPage++;
        if(mMainData.size() - ((mPage-1)*mNumber) < 0){
            mIsTheLoding = true;
        }else{
            if(mMainData.size() >= mPage * mNumber){
                startPage = (mPage-1) * mNumber;
                endPage = mPage * mNumber;
            }else{
                startPage = (mPage-1) * mNumber;
                endPage = mMainData.size();
            }
            for(int i=startPage; i< endPage; i++){
                mData.add(mMainData.get(i));
            }
            mRecordAdapter.notifyDataSetChanged();
            mIsTheLoding = false;
        }
        mProgressBar.setVisibility(View.GONE);
    }
    @Override
    public void onRefresh() {
        setListRefresh();
        // TODO Auto-generated method stub
    }

    public void onLoadMore(){
        mProgressBar.setVisibility(View.VISIBLE);
        setListOnLoad();
    }
    public void getTitle(String count){
        mTextViewTopName.setText(getString(R.string.my)+"("+count+")");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("TEST", "onDestroyView()");
        setReset();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying()){
                isPlaying = false;
                if(mMyThread != null && mMyThread.isAlive()) {
                    mMyThread.interrupt();
                }
                mMediaPlayer.pause();
            }
        }
    }
    public void setReset(){
        isPlaying = false;
        if(mMyThread != null && mMyThread.isAlive()) {
            mMyThread.interrupt();
        }
        mSeekBar.setMax(0);
        mSeekBar.setProgress(0);
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(0);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        setLayoutReset();
    }
    @Override
    public void Tab(int tab) {
        setReset();
        if(tab == 6) {
            boolean isRecordRefrash = SharedPreference.getBooleanSharedPreference(mContext, Config.RECORD_REFRASH);
            if(isRecordRefrash){
                SharedPreference.putSharedPreference(mContext, Config.RECORD_REFRASH, false);
                mProgressBar.setVisibility(View.VISIBLE);
                setList();
            }
        }
    }
    public void setTimer2(int value){
        String m = (String.valueOf(value / 60000).length() == 1)?  ("0" + (value / 60000)) : (String.valueOf(value / 60000));
        String s = (String.valueOf((value%60000)/1000).length() == 1)?  ("0" + ((value%60000)/1000)) : (String.valueOf((value%60000)/1000));
        mTextViewTime2.setText(m+":"+s);
    }

    public void setTimer(int value){
        String m = (String.valueOf(value / 60000).length() == 1)?  ("0" + (value / 60000)) : (String.valueOf(value / 60000));
        String s = (String.valueOf((value%60000)/1000).length() == 1)?  ("0" + ((value%60000)/1000)) : (String.valueOf((value%60000)/1000));
        mTextViewTime.setText(m+":"+s);
    }

}

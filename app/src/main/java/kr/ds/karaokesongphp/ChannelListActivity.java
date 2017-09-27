package kr.ds.karaokesongphp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import kr.ds.fragment.BaseFragment;
import kr.ds.fragment.ChannelFragment;
import kr.ds.fragment.ChannelListFragment;
import kr.ds.handler.ChannelHandler;
import kr.ds.handler.ListHandler;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2017-06-14.
 */
public class ChannelListActivity extends BaseActivity{

    private BaseFragment mFragment = null;
    private FragmentManager mFm;
    private FragmentTransaction mFt;

    private ChannelHandler mSavedata;
    private Toolbar mToolbar;
    private TextView mTopName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mSavedata = (ChannelHandler) savedInstanceState.getParcelable("data");
        }else{
            mSavedata = (ChannelHandler) getIntent().getParcelableExtra("data");
        }

        setContentView(R.layout.activity_channel_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTopName = (TextView) findViewById(R.id.textView_top_name);
        if (mToolbar != null) {
            if(!DsObjectUtils.isEmpty(mSavedata.getTitle()) && !DsObjectUtils.isEmpty(mSavedata.getTotal())){
                mTopName.setText(mSavedata.getTitle()+"("+mSavedata.getTotal()+")");
            }else{
                mTopName.setText(getResources().getString(R.string.app_name));
            }
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFm = getSupportFragmentManager();
        mFt = mFm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", mSavedata);
        mFragment = BaseFragment.newInstance(ChannelListFragment.class, bundle);
        mFt.replace(R.id.content_frame, mFragment);
        mFt.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("data", mSavedata);
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

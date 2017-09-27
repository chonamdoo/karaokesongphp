package kr.ds.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ds.config.Config;
import kr.ds.karaokesongphp.R;
import kr.ds.handler.VersionCheckHandler;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;
import kr.ds.utils.VersionUtils;
import kr.ds.utils.gcmHandler;


		public class SettingFragment extends BaseFragment implements OnClickListener{

	private View mView;
	private CheckBox mCheckBoxPush;
	private CheckBox mCheckBoxPushAuto;
	private CheckBox mCheckBoxPushRecord;
	private CheckBox mCheckBoxPushEnd;
	private ArrayList<VersionCheckHandler> DATA = new ArrayList<VersionCheckHandler>();
    
	private Context mContext;
	private String regId;
	private String androidId;
	private String url = Config.URL+Config.URL_XML+Config.URL_SEND_CHECK;
	private String updateurl = Config.URL+Config.URL_XML+Config.URL_SEND_UPDATE;
	private TextView mTextViewVersion;
	private LinearLayout mLinearLayoutPush;
	private LinearLayout mLinearLayoutArea;
	private boolean isArea = false;
	private ScrollView mScrollView;


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext = getActivity();	
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.setting, container, false);
		mScrollView = (ScrollView)mView.findViewById(R.id.scrollView);
		mLinearLayoutPush = (LinearLayout)mView.findViewById(R.id.linearLayout_push);
		(mCheckBoxPush = (CheckBox)mView.findViewById(R.id.checkBox_push)).setOnClickListener(this);
		(mCheckBoxPushRecord = (CheckBox)mView.findViewById(R.id.checkBox_youtube_play_record)).setOnClickListener(this);
		(mCheckBoxPushAuto = (CheckBox)mView.findViewById(R.id.checkBox_youtube_auto_play)).setOnClickListener(this);
		(mCheckBoxPushEnd = (CheckBox)mView.findViewById(R.id.checkBox_youtube_play_end_record_end)).setOnClickListener(this);
		mTextViewVersion = (TextView)mView.findViewById(R.id.textView_version);
		return mView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mTextViewVersion.setText(new VersionUtils().VersionName(mContext));

		if(!DsObjectUtils.getInstance(getActivity()).isEmpty(SharedPreference.getSharedPreference(getActivity(), Config.TOKEN))){
			mLinearLayoutPush.setVisibility(View.VISIBLE);
			regId = SharedPreference.getSharedPreference(mContext, Config.TOKEN);
			androidId = SharedPreference.getSharedPreference(mContext, Config.ANDROID_ID);

			Log.i("TEST",androidId+"");
			if(!DsObjectUtils.getInstance(mContext).isEmpty(regId) && !DsObjectUtils.getInstance(mContext).isEmpty(androidId)) {
				new regSendCheckTask().execute(androidId, regId, "", url);
			}
		}else{
			mLinearLayoutPush.setVisibility(View.GONE);
		}

		if(SharedPreference.getBooleanSharedPreference(mContext, Config.YOUTUBE_PLAY_RECORD)){
			setBackgroundChecked(mCheckBoxPushRecord, true);
		}else{
			setBackgroundChecked(mCheckBoxPushRecord, false);
		}

		if(SharedPreference.getBooleanSharedPreference(mContext, Config.YOUTUBE_AUTO_PLAY)){
			setBackgroundChecked(mCheckBoxPushAuto, true);
		}else{
			setBackgroundChecked(mCheckBoxPushAuto, false);
		}

		if(SharedPreference.getBooleanSharedPreference(mContext, Config.YOUTUBE_PLAY_END_RECORD_END)){
			setBackgroundChecked(mCheckBoxPushEnd, true);
		}else{
			setBackgroundChecked(mCheckBoxPushEnd, false);
		}

	}

	@Override
	public void Tab(int tab) {
		mScrollView.smoothScrollTo(0,0);

	}

	private class regSendCheckTask extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			return new gcmHandler().HttpPostData(params[0], params[1],params[2],params[3]);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.trim().matches("Y")){//gcm id값이 없을경우
				setBackgroundChecked(mCheckBoxPush, true);
			}else{
				setBackgroundChecked(mCheckBoxPush, false);
			}
		}
	}
	
	public void setBackgroundChecked(CheckBox toggleButton, boolean ischeck){
		if(ischeck == false){
			toggleButton.setChecked(false);
		}else{
			toggleButton.setChecked(true);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.checkBox_push:
				if(mCheckBoxPush.isChecked() == true){
					new regSendCheckTask().execute(androidId, regId, "Y",updateurl);
					setBackgroundChecked(mCheckBoxPush, true);
				}else{
					new regSendCheckTask().execute(androidId, regId, "N",updateurl);
					setBackgroundChecked(mCheckBoxPush, false);
				}
				break;
			case R.id.checkBox_youtube_play_record:
				if(mCheckBoxPushRecord.isChecked() == true){
					setBackgroundChecked(mCheckBoxPushRecord, true);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_PLAY_RECORD, true);
				}else{
					setBackgroundChecked(mCheckBoxPushRecord, false);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_PLAY_RECORD, false);
				}
				break;
			case R.id.checkBox_youtube_auto_play:
				if(mCheckBoxPushAuto.isChecked() == true){
					setBackgroundChecked(mCheckBoxPushAuto, true);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_AUTO_PLAY, true);
				}else{
					setBackgroundChecked(mCheckBoxPushAuto, false);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_AUTO_PLAY, false);
				}
				break;
			case R.id.checkBox_youtube_play_end_record_end:
				if(mCheckBoxPushEnd.isChecked() == true){
					setBackgroundChecked(mCheckBoxPushEnd, true);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_PLAY_END_RECORD_END, true);
				}else{
					setBackgroundChecked(mCheckBoxPushEnd, true);
					SharedPreference.putSharedPreference(mContext, Config.YOUTUBE_PLAY_END_RECORD_END, true);
				}
				break;
			}
	}


}

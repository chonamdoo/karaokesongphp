package kr.ds.store;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kr.ds.config.Config;
import kr.ds.karaokesongphp.R;

/*
 * 종료 시 팝업 관련
 * 티스토어 및 마켓 구분 onclick 리스너 수정
 */
@SuppressLint("ValidFragment")
public class MainStoreTypeDialog extends DialogFragment implements OnClickListener{
	private Button mButton1, mButton2, mButton3, mButton4, mButton5;
	//private CheckBox mCheckBox;
	//private ResultListner mResultListner;//리턴콜백

	private TextView mTextView1, mTextView2, mTextView3, mTextView4;


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	public MainStoreTypeDialog() {
		// TODO Auto-generated constructor stub
	}

	private DialogResultListner mDialogResultListner;
	public interface DialogResultListner {
		public void onCancel();
	}
	public MainStoreTypeDialog callback (DialogResultListner dialogresultlistner){
		this.mDialogResultListner = dialogresultlistner;
		return this;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
		View view = mLayoutInflater.inflate(R.layout.dialog, null);
		(mTextView1 = (TextView)view.findViewById(R.id.textView1)).setOnClickListener(this);
		(mTextView3 = (TextView)view.findViewById(R.id.textView3)).setOnClickListener(this);
		(mTextView4 = (TextView)view.findViewById(R.id.textView4)).setOnClickListener(this);

		if(Config.TYPE == Config.TSTORE){
			mTextView1.setVisibility(View.GONE);
		}


		mBuilder.setView(view);
		return mBuilder.create();
	}

	private void MarketLink(int type){
		switch (type) {
			case 1:
				try {
					startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id="+ Config.APP_DOWN_URL_MARKET)));
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), R.string.popupview_null,Toast.LENGTH_SHORT).show();
				}

				break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.textView1: //리뷰 달기
				if(Config.TYPE == Config.MARKET){
					MarketLink(1);
				}
				break;
			case R.id.textView3:
				dismiss();
				break;
			case R.id.textView4:
				getActivity().finish();
				break;
			default:
				break;
		}
	}
}

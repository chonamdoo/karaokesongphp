package kr.ds.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.Callable;

/**
 * CustomAsyncTask 커스텀 AsyncTask Callable 위임
 * @author Chodongsuk
 * @since 20150128
 * @param <T>
 * 
 */
/*
 * public void load(final String url) {
		new CustomAsyncTask<String>()
		.setCallable(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return "비동기처리완료";
			}
		})
		.setCallback(new AsyncTaskCallback<String>() {

			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				Log.i(TAG,"onPreExecute()"+"");
			}

			@Override
			public void onPostExecute(String result) {
				// TODO Auto-generated method stub
				Log.i(TAG,"onPostExecute()"+result+"");
			}

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				Log.i(TAG,"onCancelled()"+"");
			}

			@Override
			public void Exception(java.lang.Exception e) {
				// TODO Auto-generated method stub
				Log.i(TAG,"Exception()"+"");
				
			}
		})
		.execute();
	}
 */
public class DsAsyncTask<T> extends AsyncTask<Void, Integer, T> {
	private static final String TAG = "CustomAsyncTask";
	private DsAsyncTaskCallback<T> mCallback;
	/**
	 * java.util.concurrent.Callable 인터페이스로 비동기로 실행할 작업을 제공한다.
	 */
	private Callable<T> mCallable; 
	private Exception mException;
	
	public DsAsyncTask<T> setCallable(Callable<T> callable) {
		this.mCallable = callable;
		return this;
	}
	
	public DsAsyncTask<T> setCallback(DsAsyncTaskCallback<T> callback) {
		this.mCallback = callback;
		return this;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(this.mCallback != null){
			this.mCallback.onPreExecute();
		}
	}
	@Override
	protected T doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			return mCallable.call();
		} catch (Exception ex) {
			Log.e(TAG,"exception occured while doing in background: " + ex.getMessage(), ex);
			this.mException = ex;
			return null;
		}
	}
	@Override
	protected void onPostExecute(T result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (isException()) {
			if (mCallback != null){
				mCallback.Exception(mException);
				return;
			}
		}
		if(this.mCallback != null){
			this.mCallback.onPostExecute(result);
		}
	}
	private boolean isException() {
		return mException != null;
	}
	
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		if(this.mCallback != null){
			this.mCallback.onCancelled();
		}
	}


}

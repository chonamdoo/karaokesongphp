package kr.ds.data;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsObjectUtils;

public class GcmData extends BaseData {
    private Context mContext;
    private BaseResultListener mResultListener;
    private String URL = "";
    private String PARAM = "";
    private String mData;
    private HashMap<String, String> mDataPost;
    public GcmData(Context context){
        mContext = context;
    }

    @Override
    public BaseData clear() {
        // TODO Auto-generated method stub
        if (mData != null) {
            mData = null;
        }
        mData = new String();
        return this;
    }

    @Override
    public BaseData setUrl(String url) {
        // TODO Auto-generated method stub
        if(DsObjectUtils.getInstance(mContext).isEmpty(URL)){
            URL = url;
        }
        return this;
    }

    @Override
    public BaseData setParam(String param) {
        // TODO Auto-generated method stub
        PARAM = param;
        return this;
    }

    @Override
    public BaseData getView() {
        // TODO Auto-generated method stub

        return this;
    }

    @Override
    public <T> BaseData setCallBack(T resultListener) {
        // TODO Auto-generated method stub
        mResultListener = (BaseResultListener) resultListener;
        return this;
    }

    @Override
    public <T> BaseData getViewPost(final T post) {
        // TODO Auto-generated method stub
        new DsAsyncTask<String>().setCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //Log.i("TEST",URL + PARAM+"");
                String result = new DsHttpClient().HttpPost((HashMap<String, String>) post, URL + PARAM,"euc-kr");
                JSONObject jsonObject = new JSONObject(result);
                return new JSONObject(jsonObject.getString("summery")).getString("result");
            }
        }).setCallback(new DsAsyncTaskCallback<String>() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(String result) {
                Log.i("TEST", result+"");
                if (result.matches("success") ) {
                    if (mResultListener != null) {
                        mResultListener.OnComplete();
                    }
                } else {
                    if (mResultListener != null) {
                        mResultListener.OnMessage("result_error");
                    }
                }
            }

            @Override
            public void onCancelled() {
            }

            @Override
            public void Exception(Exception e) {
                if (mResultListener != null) {
                    mResultListener.OnMessage(e.getMessage()+"");
                }
            }
        }).execute();
        return this;
    }



}

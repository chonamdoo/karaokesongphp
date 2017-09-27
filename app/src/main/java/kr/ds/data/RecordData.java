package kr.ds.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.db.RecordDB;
import kr.ds.handler.RecordHandler;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsObjectUtils;

/**
 * Created by Administrator on 2016-08-31.
 */
public class RecordData extends BaseData {

    private String URL = "";
    private String PARAM = "";

    private RecordHandler mRecordHandler;
    private ArrayList<RecordHandler> mData;
    private BaseResultListener mResultListener;
    private RecordDB mRecordDB;
    private Cursor mCursor;
    private Context mContext;
    public RecordData(Context context){
        mContext = context;
    }

    @Override
    public BaseData clear() {
        if (mData != null) {
            mData = null;
        }
        mData = new ArrayList<RecordHandler>();
        if (mRecordHandler != null) {
            mRecordHandler = null;
        }
        mRecordHandler = new RecordHandler();

        mRecordDB = new RecordDB(mContext);

        return this;
    }

    @Override
    public BaseData setUrl(String url) {
        if(DsObjectUtils.isEmpty(URL)){
            URL = url;
        }
        return this;
    }
    @Override
    public BaseData setParam(String param) {
        PARAM = param;
        return this;
    }

    @Override
    public BaseData getView() {
        new DsAsyncTask<String[]>().setCallable(new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                Log.i("TEST",URL + PARAM+"");

                mRecordDB.open();
                mCursor = mRecordDB.fetchAllForType();
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    mData.add(new RecordHandler());
                    if (mData.size() > 0) {
                        mRecordHandler = mData.get(mData.size() - 1);
                        mRecordHandler.setContents_id(mCursor.getString(1));
                        mRecordHandler.setImage(mCursor.getString(2));
                        mRecordHandler.setTitle(mCursor.getString(3));
                        mRecordHandler.setVideo_id(mCursor.getString(4));
                        mRecordHandler.setUrl_file(mCursor.getString(5));
                        mRecordHandler.setRegdate(mCursor.getString(6));
                    }
                    mCursor.moveToNext();
                }
                mCursor.close();
                mRecordDB.close();

                String[] summery = new String[2];
                summery[0] = "success";
                summery[1] = "";
                return summery;

            }
        }).setCallback(new DsAsyncTaskCallback<String[]>() {
            @Override
            public void onPreExecute() {
            }
            @Override
            public void onPostExecute(String[] result) {
                if (result[0].matches("success")) {
                    if (mResultListener != null) {
                        mResultListener.OnComplete(mData);
                        mResultListener.OnMessage(result[1]);
                    }
                } else {
                    if (mResultListener != null) {
                        mResultListener.OnComplete(null);
                        mResultListener.OnMessage(result[1]);
                    }
                }
            }
            @Override
            public void onCancelled() {
            }
            @Override
            public void Exception(Exception e) {
                if (mResultListener != null) {
                    mResultListener.OnMessage(e.getMessage() + "");
                }
            }
        }).execute();
        return this;
    }

    @Override
    public <T> BaseData getViewPost(T post) {
        return this;
    }

    @Override
    public <T> BaseData setCallBack(T resultListener) {
        mResultListener = (BaseResultListener) resultListener;
        return this;
    }


}

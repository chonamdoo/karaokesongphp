package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-08-05.
 */
public class SearchLogHandler {

    public String message;


    public SearchLogHandler(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

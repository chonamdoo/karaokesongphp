package kr.ds.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

/**
 * Created by Administrator on 2015-02-12.
 */
public class DsGpsStateReceiver extends BroadcastReceiver {
    public static final IntentFilter GPS_INTENT_FILTER = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);


    private GpsStateListener mGpsStateListener;
    public DsGpsStateReceiver() {
    }
    public DsGpsStateReceiver(GpsStateListener listener) {
        mGpsStateListener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            mGpsStateListener.onChange();
        }
    }
    public interface GpsStateListener {
        public void onChange();
    }
}

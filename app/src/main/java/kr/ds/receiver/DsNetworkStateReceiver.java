package kr.ds.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

/**
 * 네트워크 실시간 접속 상태
 * @author Chodongsuk
 * @since 20150129
 * 
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * 
 * private BroadcastReceiver mNetworkReveiver;
 * mNetworkReveiver = new DsNetworkStateReceiver(new DsNetworkStateReceiver.NetworkStateListener() {
            @Override
            public void onConnect() {
                // connect
            }
            @Override
            public void onDisConnect() {
                // disconnect
            }
        });
   registerReceiver(mNetworkReveiver,DsNetworkStateReceiver.NETWORK_INTENT_FILTER);
        
   unregisterReceiver(mNetworkReveiver);
 */

public class DsNetworkStateReceiver extends BroadcastReceiver {
	//android.net.conn.CONNECTIVITY_CHANGE
    public static final IntentFilter NETWORK_INTENT_FILTER = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    private static final String TAG = DsNetworkStateReceiver.class.getSimpleName();
    private static boolean sBeforeNetworkContext = false; 
    private NetworkStateListener mNetworkStateListener;
    public DsNetworkStateReceiver() {
    }

    public DsNetworkStateReceiver(NetworkStateListener listener) {
        mNetworkStateListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        
    	final Bundle extra = intent.getExtras();
        if (extra == null) {
            return;
        }
        Log.d(TAG, "=====================================");
        Log.d(TAG, "EXTRA_EXTRA_INFO: " + extra.getString(ConnectivityManager.EXTRA_EXTRA_INFO));
        Log.d(TAG, "EXTRA_IS_FAILOVER: " + extra.getString(ConnectivityManager.EXTRA_IS_FAILOVER));
        //Log.d(TAG, "EXTRA_NETWORK_INFO: " + extra.getString(ConnectivityManager.EXTRA_NETWORK_INFO));
        Log.d(TAG, "EXTRA_NETWORK_TYPE: " + extra.getString(ConnectivityManager.EXTRA_NETWORK_TYPE));
        Log.d(TAG, "EXTRA_NO_CONNECTIVITY: " + extra.getString(ConnectivityManager.EXTRA_NO_CONNECTIVITY));
        Log.d(TAG, "EXTRA_REASON: " + extra.getString(ConnectivityManager.EXTRA_REASON));
        Log.d(TAG, "ConnectionArrive? : " + DsNetworkUtils.isConnected(context));
        Log.d(TAG, "=====================================\n");

        if(mNetworkStateListener == null) {
            return;
        }

        final boolean nowNetworkContext = DsNetworkUtils.isConnected(context);
        if (sBeforeNetworkContext == nowNetworkContext) {
            return;
        }

        sBeforeNetworkContext = nowNetworkContext;

        if(DsNetworkUtils.isConnected(context)){
            mNetworkStateListener.onConnect();
        } else  {
            mNetworkStateListener.onDisConnect();
        }
    }

    public interface NetworkStateListener {
        public void onConnect();
        public void onDisConnect();
    }
}

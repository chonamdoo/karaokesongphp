package kr.ds.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	private Context mContext;
	public NetworkUtils(Context context){
		mContext = context;
	}
	
	public boolean isonline() 
	{
		boolean datalive = true;
		ConnectivityManager connec = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = connec
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
			datalive = true; 
		}
		if (mobile.isConnected() || mobile.isConnectedOrConnecting()) {
			datalive = true;
		} else if (wifi.isConnected() || wifi.isConnectedOrConnecting())
		{
			datalive = true;
		}
		else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			datalive = false;
		}
		return datalive;
	}
	
	

}

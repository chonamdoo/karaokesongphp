package kr.ds.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Random;
import java.util.UUID;

public class UniqueID {

	public static String getUniqueID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();
		return deviceId;
	}

	public static String getUniqueID() {
		Random rnd =new Random();
		StringBuffer buf =new StringBuffer();

		for(int i=0;i<7;i++){
			if(rnd.nextBoolean()){
				buf.append((char)((int)(rnd.nextInt(26))+97));
			}else{
				buf.append((rnd.nextInt(10)));
			}
		}
		return buf.toString();
	}

	public static int getRandomNumber(int a) {
		Random rnd =new Random();
		StringBuffer buf =new StringBuffer();
		buf.append(rnd.nextInt(a));
		return Integer.parseInt(buf.toString());
	}


}

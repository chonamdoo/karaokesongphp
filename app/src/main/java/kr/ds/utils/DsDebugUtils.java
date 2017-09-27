package kr.ds.utils;

import android.util.Log;

/**
 * 디버그 유틸
 * 
 * @author Chodongsuk
 * @since 2015.02.02
 * 
 */
public class DsDebugUtils {

	public static void Message(String str) {
		Log.d("TAG", "[" + new Throwable().getStackTrace()[0].getFileName()
				+ "][" + new Throwable().getStackTrace()[0].getMethodName()
				+ "][" + new Throwable().getStackTrace()[0].getLineNumber()
				+ "] : " + str);
	}
}

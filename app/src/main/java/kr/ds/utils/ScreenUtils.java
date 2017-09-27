package kr.ds.utils;

import android.content.Context;
import android.util.TypedValue;

public class ScreenUtils {
	
	private static ScreenUtils objInstance;
	
	private ScreenUtils(){
		
	}
	
	public static ScreenUtils getInstacne(){
		if(objInstance == null){
			objInstance = new ScreenUtils();
		}
		return objInstance;
	}
	
	public int getPixelFromDPI(Context context, float dpi){
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, context.getResources().getDisplayMetrics()));
	}






}

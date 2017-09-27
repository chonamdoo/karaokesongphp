package kr.ds.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016-04-19.
 */
public class DsKeyBoardUtils {

    private static DsKeyBoardUtils keyBoardUtils = null;

    public DsKeyBoardUtils(){

    }
    public static DsKeyBoardUtils getInstance(){
        if(keyBoardUtils == null){
            synchronized (DsKeyBoardUtils.class){
                if(keyBoardUtils == null){
                    keyBoardUtils = new DsKeyBoardUtils();
                }
            }
        }
        return keyBoardUtils;
    }

    public void hideKeyboard(Activity activity){
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

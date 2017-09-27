package kr.ds.clipboard;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Access the system clipboard using the new {@link ClipboardManager} introduced with API 11
 */
@TargetApi(11)
public class ClipboardManagerApi11 extends kr.ds.clipboard.ClipboardManager {

    public ClipboardManagerApi11(Context context) {
        super(context);
    }
    

    @Override
    public void setText(String label, String text) {
        ClipboardManager clipboardManager =
                (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboardManager.setPrimaryClip(clip);
        if(mOnChangeListner != null){
        	mOnChangeListner.change(text);
        }
    }
}




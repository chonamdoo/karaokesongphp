package kr.ds.karaokesongphp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016-08-31.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }

    public boolean isFaceBookCheck() {
        Intent startLink = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
        if(startLink != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}

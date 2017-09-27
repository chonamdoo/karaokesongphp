package kr.ds.karaokesongphp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import kr.ds.config.Config;
import kr.ds.handler.WebHandler;
import kr.ds.utils.PushWakeLock;

/**
 * Created by Administrator on 2017-07-20.
 */

public class DownActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        PushWakeLock.acquire(this);
        alert();
        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                PushWakeLock.release();
            }
        };
        handler.sendEmptyMessageDelayed(0, 100);
    }

    public void alert() {
        Bundle Bun = getIntent().getExtras();
        String Text = Bun.getString("text");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setPositiveButton("닫기",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PushWakeLock.release();
                        finish();
                    }
                });

        alertDialog.setNegativeButton("다운로드하기",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent NextIntent = new Intent(getApplicationContext(), DownWebViewActivity.class);
                        WebHandler data = new WebHandler(Config.URL+ Config.URL_XML+ Config.URL_DOWN);
                        NextIntent.putExtra("data", data);
                        startActivity(NextIntent);
                        PushWakeLock.release();
                        finish();
                    }
                });
        alertDialog.setMessage(Text);
        alertDialog.setTitle("알림");
        alertDialog.show();
    }
}

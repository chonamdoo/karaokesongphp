package kr.ds.karaokesongphp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

import kr.ds.handler.RecordHandler;

/**
 * Created by Administrator on 2017-09-08.
 */

public class EffectActivity extends BaseActivity{
    private RecordHandler mSavedata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFFmpeg();
        if(savedInstanceState != null){
            mSavedata = (RecordHandler) savedInstanceState.getParcelable("data");
        }else{
            mSavedata = (RecordHandler) getIntent().getParcelableExtra("data");
        }
        setFile(mSavedata.getUrl_file());
    }

    private boolean FileExists(String url) {
        File files = new File(url);
        Boolean check;
        if (files.exists() == true) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    private void SetMkdirs(String url) {
        try {
            File path = new File(url);
            if (!path.isDirectory()) {
                path.mkdirs();
            }
        } catch (Exception e) {
        }
    }

    private void setFile(String mrfile){
        if (FileExists(Environment.getExternalStorageDirectory().getAbsolutePath()+"/karaokesongphp/effect/") == false) {
            SetMkdirs(Environment.getExternalStorageDirectory().getAbsolutePath()+"/karaokesongphp/effect/");
        }
        StringBuilder cmd = new StringBuilder();

        //String cmd = "-y -i " + mrfile +" -af pan=stereo|c0=c0|c1=-1*c1 -ac 1 "+ new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/karaokesongphp/effect/", mrfile+".wav").getAbsolutePath();
/*
        String[] command = new String[8];

        command[0] = "-y";
        command[1] = "-i";
        command[2] = mrfile;
        command[3] = "-af";
        command[4] = "pan=stereo|c0=c0|c1=-1*c1";
        command[5] = "-ac";
        command[6] = "1";
        command[7] = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/karaokesongphp/effect/", mSavedata.getContents_id()+".wav").getAbsolutePath();
*/
        String[] command = new String[10];
        command[0] = "-y";
        command[1] = "-i";
        command[2] = mrfile;
        command[3] = "-map";
        command[4] = "0";
        command[5] = "-c:v";
        command[6] = "copy";
        command[7] = "-af";
        command[8] = "flanger=0:5:0:71:5:sinusoidal:50:linear";
        command[9] = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/karaokesongphp/effect/", mSavedata.getContents_id()+".wav").getAbsolutePath();




        FFmpeg ffmpeg = FFmpeg.getInstance(getApplicationContext());
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String message) {
                    Log.i("ffmpeg-onFailure", message);

                }

                @Override
                public void onSuccess(String message) {
                    Log.i("ffmpeg-onSuccess", message);

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e){
            Log.i("ffmpeg", e.getMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("data", mSavedata);
        super.onSaveInstanceState(outState);
    }

    private void loadFFmpeg() {
        FFmpeg ffmpeg = FFmpeg.getInstance(EffectActivity.this.getApplicationContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }
}

package kr.ds.karaokesongphp;

/**
 * Created by Manoj on 11-02-2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.HashMap;

import kr.ds.config.Config;
import kr.ds.data.BaseResultListener;
import kr.ds.data.GcmData;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;


public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    public RegistrationIntentService() {
        super(TAG);
    }
    private String android_id = "";
    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            String old_token = sharedPreferences.getString(Config.TOKEN , "");
            if(!DsObjectUtils.isEmpty(SharedPreference.getSharedPreference(getApplicationContext(), Config.ANDROID_ID))) {
                android_id = SharedPreference.getSharedPreference(getApplicationContext(), Config.ANDROID_ID);
            }

            if(!token.matches(old_token)) {
                sendRegistrationToServer(token, old_token, android_id, "regis");
                sharedPreferences.edit().putString(Config.TOKEN, token).apply();
                // Subscribe to topic channels
                subscribeTopics(token);
                // You should store a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
                sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER, true).apply();
                // [END register_for_gcm]
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token, String oldtoken, String android_id, String type) {
        // Add custom implementation, as needed.
        Log.i("TEST",token+"");
        HashMap<String, String> mHashMap = new HashMap<String, String>();
        mHashMap.put("reg_id", token);
        mHashMap.put("old_reg_id", oldtoken);
        mHashMap.put("android_id", android_id);
        mHashMap.put("send", "Y");
        mHashMap.put("type", type);

        if(mHashMap != null){
            new GcmData(getApplicationContext()).clear().setCallBack(new BaseResultListener() {

                @Override
                public void OnMessage(String str) {
                    // TODO Auto-generated method stub
                }
                @Override
                public <T> void OnComplete() {
                    // TODO Auto-generated method stub

                }
                @Override
                public <T> void OnComplete(Object data) {

                }


            }).setUrl(Config.URL+Config.URL_XML+Config.URL_GCM).getViewPost(mHashMap);
        }
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

}


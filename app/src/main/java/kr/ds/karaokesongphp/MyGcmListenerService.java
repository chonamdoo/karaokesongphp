/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.ds.karaokesongphp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import kr.ds.config.Config;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.Helper;
import kr.ds.utils.SharedPreference;
import kr.ds.utils.UniqueID;
import me.leolin.shortcutbadger.ShortcutBadger;


public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {


        int badgerCount = SharedPreference.getIntSharedPreference(getApplicationContext(), "badger_count");
        badgerCount++;
        SharedPreference.putSharedPreference(getApplicationContext(),"badger_count",badgerCount);
        ShortcutBadger.applyCount(getApplicationContext(), badgerCount);

        String message = data.getString("msg");
        String image_url = data.getString("image_url");
        String push_type = data.getString("push_type");
        if (!DsObjectUtils.getInstance(this).isEmpty(push_type)) {
            if(push_type.matches("down")){
                sendDownNotification(message);
            }else{
                if (!DsObjectUtils.getInstance(this).isEmpty(image_url)) {
                    Bitmap bitmap = Helper.tryToGetBitmapFromInternet(image_url, this, -1);
                    sendNotification(message, bitmap);
                } else {
                    sendNotification(message);
                }
            }
        }else{
            if(!DsObjectUtils.getInstance(this).isEmpty(image_url)){
                Bitmap bitmap = Helper.tryToGetBitmapFromInternet(image_url, this, -1);
                sendNotification(message,  bitmap);
            }else{
                sendNotification(message);
            }
        }



        Intent newintent = new Intent(Config.GCM_INTENT_FILLTER);
        newintent.putExtra("data", data);
        sendBroadcast(newintent);

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendDownNotification(String message) {
        Intent i = new Intent(getApplicationContext(), DownActivity.class);
        Bundle b = new Bundle();
        b.putString("text", message);
        i.putExtras(b);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void sendNotification(String message) {
        Intent intent = new Intent(this, IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setSmallIcon(R.mipmap.icon)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UniqueID.getRandomNumber(1000), notificationBuilder.build());
    }
    private void sendNotification(String message, Bitmap bitmap) {
        Intent intent = new Intent(this, IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setSmallIcon(R.mipmap.icon)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if(bitmap != null){
            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
            style.setBigContentTitle(getString(R.string.app_name));
            style.setSummaryText(message);
            style.bigPicture(bitmap);
            notificationBuilder.setStyle(style);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UniqueID.getRandomNumber(1000), notificationBuilder.build());
    }
}

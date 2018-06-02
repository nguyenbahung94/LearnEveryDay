package com.ekakashi.greenhouse.features.notification_setting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.Prefs;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.add_field_greenhouse.EKMainFieldActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by nbhung on 12/6/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMsgService";
    private int notificationType = 0;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, remoteMessage.toString());
        Log.e(TAG, "onMessageReceived");
        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().get("type") != null) {
                notificationType = Integer.parseInt(remoteMessage.getData().get("type"));
                updateBadgeNumber(notificationType);
            }
        }

        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        } else {
            sendNotification(remoteMessage.getData().get("body"),
                    remoteMessage.getData().get("title"));
        }
    }

    private void sendNotification(String body, String title) {
        Log.e(TAG, "sendNotification");
        Intent intent = new Intent(this, EKMainFieldActivity.class);
        intent.putExtra(Utils.Name.NOTIFICATION_TYPE, notificationType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("ChannelID", "ChannelName",
                    NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("This is description");
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }

            Notification n = new Notification.Builder(this, "ChannelID")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pendingIntent)
                    .setBadgeIconType(R.drawable.ic_notification)
                    .setNumber(5)
                    .setColor(getResources().getColor(R.color.colorAccent, null))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                    .setSmallIcon(R.drawable.ic_notification)
                    .setAutoCancel(true)
                    .build();

            if (notificationManager != null) {
                notificationManager.notify(0, n);
            }
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification))
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(title)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(uri)
                    .setContentIntent(pendingIntent);
            if (notificationManager != null) {
                notificationManager.notify(0, builder.build());
            }
        }
    }

    private void updateBadgeNumber(int notificationType) {

        if (notificationType == Utils.Constant.NOTIFICATION_TYPE_NEWS) {
            int badgeSetting = Prefs.getInstance(this).getBadgeSetting() + 1;
            Prefs.getInstance(this).saveBadgeSetting(badgeSetting);
        } else {
            int badgeTimeline = Prefs.getInstance(this).getBadgeTimeline() + 1;
            Prefs.getInstance(this).saveBadgeTimeline(badgeTimeline);
        }
        //send message to main
        Intent intent = new Intent(TAG);
        sendBroadcast(intent);
    }
}

package com.android.project.orderfoodsserver_app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.android.project.orderfoodsserver_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import com.android.project.orderfoodsserver_app.MainActivity;
import com.android.project.orderfoodsserver_app.OrderStatusActivity;

import com.android.project.orderfoodsserver_app.common.Common;
import com.android.project.orderfoodsserver_app.helper.NotificationHelper;
import com.android.project.orderfoodsserver_app.model.Token;

/**
 * Created by kenhoang on 09/02/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        super.onMessageReceived(remoteMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) sendNotificaionAPI26(remoteMessage);
        else sendNotification(remoteMessage);
    }

    private void sendNotificaionAPI26(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title = notification.getTitle();
        String content = notification.getBody();
        //Here we will fix to click to notification -> go to order list
        Intent intent = new Intent(this, OrderStatusActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper helper = new NotificationHelper(this);
        Notification.Builder builder = helper.getFoodFastChannelNotification(title, content, pendingIntent, defaultSoundUri);
        //Gen random Id for notification to show all notification
        helper.getManager().notify(new Random().nextInt(), builder.build());
    }

    @Override
    public void onNewToken(String tokenRefreshed) {
        super.onNewToken(tokenRefreshed);
        if (Common.currentUser != null) updatetokenToFirebase(tokenRefreshed);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager noti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        noti.notify(0, builder.build());
    }

    private void updatetokenToFirebase(String tokenRefreshed) {
        DatabaseReference tokenDB = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(tokenRefreshed, false); //false because this token send from Client app
        tokenDB.child(Common.currentUser.getPhone()).setValue(token);

    }
}

package com.ved.framework.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class NotificationUtils {

    public static Notification showNotification(Context mContext, @NonNull Class<?> cls, String title, String content, @DrawableRes int icon, int importance){

        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //准备intent
        Intent intent = new Intent();
        intent.setClass(mContext, cls);

        //notification
        Notification notification = null;
        String contentText;
        // 构建 PendingIntent
        PendingIntent pi = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new NotificationCompat.Builder(mContext)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(icon)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new Notification.Builder(mContext)
                    .setAutoCancel(true)//设置点击通知栏消息后，通知消息自动消失
                    .setContentIntent(pi)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID = "patrol_work_channel_id";
            CharSequence name = "patrol_work_channel";
            String Description = "patrol work channel";
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 100, 200});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);

            notification = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(importance)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        }

        notificationManager.notify(getNotifyId(), notification);
        return notification;
    }


    public static Notification showNotification(Context mContext,String title,String content, @DrawableRes int icon,int importance,Intent intentParam){
        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        String contentText;
        // 构建 PendingIntent
        PendingIntent pi = PendingIntent.getActivity(mContext, 1, intentParam, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new NotificationCompat.Builder(mContext)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(icon)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new Notification.Builder(mContext)
                    .setAutoCancel(true)//设置点击通知栏消息后，通知消息自动消失
                    .setContentIntent(pi)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID = "patrol_work_channel_id";
            CharSequence name = "patrol_work_channel";
            String Description = "patrol work channel";
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 100, 200});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);

            notification = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(importance)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        }

        notificationManager.notify(getNotifyId(), notification);
        return notification;
    }


    public static Notification getNotification(Context mContext,String title,String content, @DrawableRes int icon,int importance,String channelId,Intent intent){

        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //准备intent
//        intent.setClass(mContext, MessageActivity.class);

        //notification
        Notification notification = null;
        String contentText;
        // 构建 PendingIntent

        PendingIntent pi = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new NotificationCompat.Builder(mContext)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(icon)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            notification = new Notification.Builder(mContext)
                    .setAutoCancel(true)//设置点击通知栏消息后，通知消息自动消失
                    .setContentIntent(pi)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID =channelId;
            CharSequence name = "patrol_work_channel";
            String Description = "patrol work channel";
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 100, 200});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);

            notification = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(importance)
                    .setContentIntent(pi)
                    .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                    .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                    .build();

        }
        return notification;
    }

    private static int getNotifyId(){
        return (int)(Math.random()*9+1)*1000+(int)(Math.random()*9+1)*100+(int)(Math.random()*9+1)*10+(int)(Math.random()*9+1);
    }
}
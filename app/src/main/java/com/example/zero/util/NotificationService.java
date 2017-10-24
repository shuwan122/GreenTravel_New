package com.example.zero.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.zero.activity.MsgActivity;
import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/10/12.
 */

public class NotificationService extends Service {
    // 获取消息线程
    private MessageThread messageThread = null;
    // 点击查看
    private Intent messageIntent = null;
    private PendingIntent messagePendingIntent = null;
    // 通知栏消息
    //private int messageNotificationID = 1000;
    private Notification messageNotification = null;
    private NotificationManager messageNotificationManager = null;
    private Notification.Builder builder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 初始化
        messageNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        messageIntent = new Intent(this, MsgActivity.class);
        messagePendingIntent = PendingIntent.getActivity(this, 0, messageIntent, 0);
        builder = new Notification.Builder(this);
        // 开启线程
        messageThread = new MessageThread();
        messageThread.isRunning = true;
        messageThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 从服务器端获取消息
     */
    class MessageThread extends Thread {
        // 设置是否循环推送
        public boolean isRunning = true;

        public void run() {
            try {
                // 间隔时间
                Thread.sleep(1000);
                // 获取服务器消息
                String serverMessage = getServerMessage();
                if (serverMessage != null && !"".equals(serverMessage)) {
                    // 更新通知栏
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("通知的标题")
                            .setContentText("这是通知的内容")
                            .setAutoCancel(true)                           //设置点击后取消Notification
                            .setContentIntent(messagePendingIntent);       //设置PendingIntent;
                    messageNotification = builder.build();
                    messageNotificationManager.notify(1, messageNotification);
                    // 每次通知完，通知ID递增一下，避免消息覆盖掉
                    //messageNotificationID++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        // System.exit(0);
        messageThread.isRunning = false;
        super.onDestroy();
    }

    /**
     * 模拟发送消息
     *
     * @return 返回服务器要推送的消息，否则如果为空的话，不推送
     */
    public String getServerMessage() {
        return "NEWS!";
    }
}

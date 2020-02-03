package com.attafakkur.myfilmfinal.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.attafakkur.myfilmfinal.MainActivity;
import com.attafakkur.myfilmfinal.R;

import java.util.Calendar;
import java.util.Objects;

import static com.attafakkur.myfilmfinal.reminder.Constant.CHANNEL_ID;
import static com.attafakkur.myfilmfinal.reminder.Constant.CHANNEL_NAME;
import static com.attafakkur.myfilmfinal.reminder.Constant.EXTRA_MESSAGE;

/**
 * @author geek-dev
 * @date 1/27/20
 */
public class DailyReminder extends BroadcastReceiver {
    public static int NOTIFICATION_ID = 11;
    @Override
    public void onReceive(Context context, Intent intent) {
        reminderDaily(
                context,
                context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.message_daily),
                intent.getStringExtra(EXTRA_MESSAGE),
                NOTIFICATION_ID
        );
    }
    private void reminderDaily(Context context, String title, String alarm, String message, int notificationId) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,message)
                .setSmallIcon(R.drawable.ic_action_reminder)
                .setContentTitle(title)
                .setContentText(alarm)
                .setSubText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(uri)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(CHANNEL_ID);

            if(manager != null){
                manager.createNotificationChannel(channel);
            }
        }
        if(manager!=null){
            manager.notify(notificationId,builder.build());
        }
    }

    public void setDailyReminder(Context context, String type, String time, String message){
        cancelDaily(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        intent.putExtra("type",type);
        String[] times = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        Objects.requireNonNull(alarmManager).setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    public void cancelDaily(Context context){
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        Objects.requireNonNull(manager).cancel(pendingIntent);
    }
}

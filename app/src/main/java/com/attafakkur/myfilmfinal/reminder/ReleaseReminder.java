package com.attafakkur.myfilmfinal.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat.Builder;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.attafakkur.myfilmfinal.BuildConfig;
import com.attafakkur.myfilmfinal.MainActivity;
import com.attafakkur.myfilmfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.attafakkur.myfilmfinal.reminder.Constant.CHANNEL_ID_RELEASE;
import static com.attafakkur.myfilmfinal.reminder.Constant.CHANNEL_NAME_RELEASE;
import static com.attafakkur.myfilmfinal.reminder.Constant.DATE_FORMAT_MOVIE;

/**
 * @author geek-dev
 */
public class ReleaseReminder extends BroadcastReceiver {
    public static int NOTIFY_ID = 1998;

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieRelease(context);
    }

    private void getMovieRelease(Context context) {
        String now = getCurrentTime(DATE_FORMAT_MOVIE);
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie?api_key="+ BuildConfig.MovieApi +"&primary_release_date.gte="+now+"&primary_release_date.lte="+now)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.optJSONArray("results");
                            for(int i = 0 ;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
                                String id = object.getString("id");
                                String title = object.getString("title");
                                String description = object.getString("overview");
                                String releaseDate = object.getString("release_date");

                                if(releaseDate.equals(now)){
                                    remainderRelease(context,title,description,id);
                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void remainderRelease(Context context, String title, String description, String id) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(id),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Builder builder = new Builder(context)
                .setSmallIcon(R.drawable.ic_action_reminder)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_action_reminder))
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(uri)
                .setAutoCancel(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_RELEASE,
                    CHANNEL_NAME_RELEASE,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(CHANNEL_ID_RELEASE);
            if(manager != null){
                manager.createNotificationChannel(channel);
            }
        }
        if(manager!=null){
            manager.notify(Integer.parseInt(id),builder.build());
        }
    }
    public void setReleaseReminder(Context context, String type, String time, String message){
        cancelRelease(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReleaseReminder.class);
        intent.putExtra("message",message);
        intent.putExtra("type",type);
        String[] times = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID,intent,0);
        Objects.requireNonNull(alarmManager)
                .setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    public void cancelRelease(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID,intent,0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
    String getCurrentTime(String format){
        return new SimpleDateFormat(format,Locale.getDefault()).format(new Date());
    }
}

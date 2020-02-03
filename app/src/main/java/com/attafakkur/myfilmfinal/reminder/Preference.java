package com.attafakkur.myfilmfinal.reminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static com.attafakkur.myfilmfinal.reminder.Constant.DAILY;
import static com.attafakkur.myfilmfinal.reminder.Constant.ALARM_DAILY;
import static com.attafakkur.myfilmfinal.reminder.Constant.ALARM_RELEASE;
import static com.attafakkur.myfilmfinal.reminder.Constant.PREFERENCE;
import static com.attafakkur.myfilmfinal.reminder.Constant.RELEASE;

/**
 * @author geek-dev
 * @date 1/27/20
 */
public class Preference {

    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setDailyTime(String time){
        editor.putString(DAILY,time);
        editor.commit();
    }
    public void setDailyMessage(String message){
        editor.putString(ALARM_DAILY,message);
    }

    public void setReleaseTime(String time){
        editor.putString(RELEASE,time);
        editor.commit();
    }
    public void setReleaseMessage(String message){
        editor.putString(ALARM_RELEASE,message);
    }
}

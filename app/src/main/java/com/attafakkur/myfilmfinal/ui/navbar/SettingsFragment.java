package com.attafakkur.myfilmfinal.ui.navbar;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.attafakkur.myfilmfinal.R;
import com.attafakkur.myfilmfinal.reminder.DailyReminder;
import com.attafakkur.myfilmfinal.reminder.Preference;
import com.attafakkur.myfilmfinal.reminder.ReleaseReminder;

import java.util.Objects;

import static com.attafakkur.myfilmfinal.reminder.Constant.DAILY_REMINDER;
import static com.attafakkur.myfilmfinal.reminder.Constant.DAILY_TYPE;
import static com.attafakkur.myfilmfinal.reminder.Constant.KEY_DAILY;
import static com.attafakkur.myfilmfinal.reminder.Constant.KEY_RELEASE;
import static com.attafakkur.myfilmfinal.reminder.Constant.RELEASE_REMINDER;
import static com.attafakkur.myfilmfinal.reminder.Constant.RELEASE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * @author geek-dev
 */
public class SettingsFragment extends Fragment {

    private DailyReminder dailyReminder;
    private ReleaseReminder releaseReminder;
    private Preference preference;
    private Switch dailySwitch, releaseSwitch;

    private SharedPreferences daily,release;
    private String timeDaily = "07:00";
    private String timeRelease = "08:00";
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailySwitch = view.findViewById(R.id.dailySwitch);
        releaseSwitch = view.findViewById(R.id.releaseSwitch);
        dailySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> setDaily(isChecked));
        releaseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> setRelease(isChecked));
        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();
        preference = new Preference(Objects.requireNonNull(getActivity()));
        setPreference();

        view.findViewById(R.id.tv_change_language).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        });

    }
    public void setDaily(boolean yes){
        SharedPreferences.Editor dailyEdit = daily.edit();
        if(yes){
            dailyEdit.putBoolean(KEY_DAILY,true);
            dailyEdit.apply();
            dailyOn();
        }else{
            dailyEdit.putBoolean(KEY_DAILY,false);
            dailyEdit.apply();
            dailyOff();
        }
    }

    private void dailyOff() {
        dailyReminder.cancelDaily(Objects.requireNonNull(getActivity()));
    }

    private void dailyOn() {
        String on = getResources().getString(R.string.daily_reminder);
        preference.setDailyTime(timeDaily);
        preference.setDailyMessage(on);
        dailyReminder.setDailyReminder(getActivity(),DAILY_TYPE,timeDaily,on);
    }

    private void setRelease(boolean yes) {
        SharedPreferences.Editor releaseEdit = release.edit();
        if(yes){
            releaseEdit.putBoolean(KEY_RELEASE,true);
            releaseEdit.apply();
            releaseOn();
        }else {
            releaseEdit.putBoolean(KEY_RELEASE,false);
            releaseEdit.apply();
            releaseOff();
        }
    }

    private void releaseOff() {
        releaseReminder.cancelRelease(Objects.requireNonNull(getActivity()));
    }

    private void releaseOn() {
        String on = getResources().getString(R.string.release_reminder);
        preference.setReleaseTime(timeRelease);
        preference.setReleaseMessage(on);
        releaseReminder.setReleaseReminder(getActivity(),RELEASE_TYPE,timeRelease,on);
    }
    private void setPreference(){
        daily = Objects.requireNonNull(this.getActivity()).getSharedPreferences(DAILY_REMINDER, Context.MODE_PRIVATE);
        boolean checkDaily = daily.getBoolean(KEY_DAILY,false);
        dailySwitch.setChecked(checkDaily);
        release = this.getActivity().getSharedPreferences(RELEASE_REMINDER,Context.MODE_PRIVATE);
        boolean checkRelease = release.getBoolean(KEY_RELEASE,false);
        releaseSwitch.setChecked(checkRelease);
    }
}

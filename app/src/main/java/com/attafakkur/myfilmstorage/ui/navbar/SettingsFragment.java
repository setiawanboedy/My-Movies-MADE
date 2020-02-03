package com.attafakkur.myfilmstorage.ui.navbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.attafakkur.myfilmstorage.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView tvChangeLanguage = root.findViewById(R.id.tv_change_language);

        tvChangeLanguage.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
    }

}
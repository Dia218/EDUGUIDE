package com.capston.eduguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capston.eduguide.user.SettingsActivity;

public class Frag5User extends Fragment {

    private View view;

    private TextView profileNameTextView;
    private TextView selfIntroTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag5_user, container, false);

        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.settings_icon);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        /*
        // 저장된 닉네임과 자기소개 가져와서 TextView에 반영하기
        profileNameTextView = view.findViewById(R.id.profile_name);
        selfIntroTextView = view.findViewById(R.id.self_intro);

        updateProfileInfo();
*/
        return view;
    }
/*
    public void updateProfileInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
        String savedName = sharedPreferences.getString("name", "");
        String savedIntro = sharedPreferences.getString("intro", "");

        profileNameTextView.setText(savedName);
        selfIntroTextView.setText(savedIntro);
    }
*/
}


package com.capston.eduguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag4Notice extends Fragment {

    private View view;

    private TextView noticeText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4_notice, container, false);
        noticeText = view.findViewById(R.id.notice_text);
        return view;
    }

    public void updateNotice(String message) {

        if (noticeText != null) {
            noticeText.setText(message);
        }

    }
}

package com.capston.eduguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bottomnavi.R;

public class Guide extends Fragment {

    private View view;
    public static Guide newInstance(int param1){
        Guide fg = new Guide();
        Bundle args = new Bundle();
        args.putInt("param1", param1);
        fg.setArguments(args);
        return fg; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag1_guide, container, false);

        return view;
    }
}

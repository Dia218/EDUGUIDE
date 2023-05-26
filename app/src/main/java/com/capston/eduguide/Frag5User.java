package com.capston.eduguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capston.eduguide.user.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Frag5User extends Fragment {

    private View view;

    private TextView profileNameTextView;
    private TextView selfIntroTextView;
    private ImageView gradeImageView;

    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag5_user, container, false);


        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.settings_icon);
        // SharedPreferences 객체 초기화
        sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        // Firebase 실시간 데이터베이스 객체 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        settingsButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 저장된 닉네임과 자기소개, 등급 가져와서 TextView에 반영하기
        profileNameTextView = view.findViewById(R.id.profile_name);
        selfIntroTextView = view.findViewById(R.id.self_intro);
        gradeImageView = view.findViewById(R.id.profile_image);


        updateProfileInfo();

        return view;
    }

    private int grade(Integer gradeInt) {
        if(gradeInt == 0)
            return R.drawable.seed;
        else if(gradeInt == 1)
            return R.drawable.sprout;
        else if(gradeInt == 2)
            return R.drawable.seedling;
        else if(gradeInt == 3)
            return R.drawable.tree;
        else if(gradeInt == 5)
            return R.drawable.grade1;
        else
            return R.drawable.grade1;
    }

    public void updateProfileInfo() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
            String savedName = sharedPreferences.getString("name", "");
            String savedIntro = sharedPreferences.getString("intro", "");

            profileNameTextView.setText(savedName);
            selfIntroTextView.setText(savedIntro);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                DatabaseReference userRef = databaseReference.child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String grade = dataSnapshot.child("grade").getValue(String.class);
                            gradeImageView.setImageResource(grade(Integer.parseInt(grade)));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 에러 처리
                    }
                });
            }
        }
    }
}


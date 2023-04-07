package com.capston.eduguide.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capston.eduguide.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_settings);

        //프로필 편집 클릭
        TextView profileEdit = findViewById(R.id.profile_edit);
        profileEdit.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // 개인정보 설정 클릭
        TextView personalinfoEdit = findViewById(R.id.personalinfo_edit);
        personalinfoEdit.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, EditpersonalinfoActivity.class);
            startActivity(intent);
        });

        //문의 클릭
        TextView inquiryWrite = findViewById(R.id.inquiry_edit);
        inquiryWrite.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, ListinquiryActivity.class);
            startActivity(intent);
        });

        //공지 클릭
        TextView noticeWrite = findViewById(R.id.notice_edit);
        noticeWrite.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, WriteNoticeActivity.class);
            startActivity(intent);
        });

        // 로그아웃 버튼 찾기
        TextView logoutButton = findViewById(R.id.logout_edit);

        // 로그아웃 버튼 클릭 리스너 등록하기
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 처리 코드 작성
                // 예시: SharedPreferences에서 로그인 정보 삭제 후 로그인 화면으로 이동
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

    }
}

package com.capston.eduguide.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capston.eduguide.R;

public class WriteNoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_notice);

        // notice1 클릭
        TextView notice1 = findViewById(R.id.notice1);
        notice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteNoticeActivity.this, UserNotice1Activity.class);
                startActivity(intent);
            }
        });

        // notice2 클릭
        TextView notice2 = findViewById(R.id.notice2);
        notice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteNoticeActivity.this, UserNotice2Activity.class);
                startActivity(intent);
            }
        });

        // notice3 클릭
        TextView notice3 = findViewById(R.id.notice3);
        notice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteNoticeActivity.this, UserNotice3Activity.class);
                startActivity(intent);
            }
        });
    }

}
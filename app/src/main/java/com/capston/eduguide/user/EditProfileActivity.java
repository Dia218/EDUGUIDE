package com.capston.eduguide.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capston.eduguide.MainActivity;
import com.capston.eduguide.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            EditText nameEditText = findViewById(R.id.profile_name_edittext);
            EditText introEditText = findViewById(R.id.self_intro_edittext);

            String newName = nameEditText.getText().toString();
            String newIntro = introEditText.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", newName);
            editor.putString("intro", newIntro);
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(EditProfileActivity.this, "프로필이 수정되었습니다.", Toast.LENGTH_SHORT).show();
            finish(); // 현재 액티비티 종료
        });

    }
}

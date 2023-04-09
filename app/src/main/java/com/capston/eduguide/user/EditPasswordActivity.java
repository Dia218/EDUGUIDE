package com.capston.eduguide.user;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capston.eduguide.R;
import com.capston.eduguide.db.DatabaseHelper;

public class EditPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_password);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            EditText currentPasswordEditText = findViewById(R.id.edittext_current_password);
            EditText newPasswordEditText = findViewById(R.id.edittext_new_password);
            EditText confirmPasswordEditText = findViewById(R.id.edittext_confirm_password);

            String currentPassword = currentPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // 새로운 비밀번호가 확인 비밀번호와 일치하는지 확인
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(EditPasswordActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 새로운 비밀번호를 DB에 저장하는 코드
            DatabaseHelper dbHelper = new DatabaseHelper(EditPasswordActivity.this, "userDB", null, 1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("password", newPassword);
            int updatedRows = db.update("userDB", values, "password=?", new String[] {currentPassword});
            db.close();

            if (updatedRows < 1) {
                Toast.makeText(EditPasswordActivity.this, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(EditPasswordActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditPasswordActivity.this, com.capston.eduguide.login.LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}

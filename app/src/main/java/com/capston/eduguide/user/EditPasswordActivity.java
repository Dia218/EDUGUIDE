package com.capston.eduguide.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.capston.eduguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_password);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        EditText currentPasswordEditText = findViewById(R.id.edittext_current_password);
        EditText newPasswordEditText = findViewById(R.id.edittext_new_password);
        EditText confirmPasswordEditText = findViewById(R.id.edittext_confirm_password);

        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // 새로운 비밀번호가 확인 비밀번호와 일치하는지 확인
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // 현재 사용자의 비밀번호 업데이트
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditPasswordActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditPasswordActivity.this, com.capston.eduguide.login.LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EditPasswordActivity.this, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}


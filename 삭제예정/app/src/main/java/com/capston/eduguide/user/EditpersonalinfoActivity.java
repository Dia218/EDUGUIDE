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

public class EditpersonalinfoActivity extends AppCompatActivity {

    EditText userName;
    EditText userBirth;
    EditText userPhone;
    EditText userEmail;

    SQLiteDatabase userDB;
    DatabaseHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_personalinfo);

        userName = findViewById(R.id.edit_text_name);
        userBirth = findViewById(R.id.edit_text_birth);
        userPhone = findViewById(R.id.edit_text_phone);
        userEmail = findViewById(R.id.edit_text_email);

        helper = new DatabaseHelper(getApplicationContext(), "userDB", null, 1);
        userDB = helper.getWritableDatabase();

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> savePersonalInfo());

        Button changePasswordButton = findViewById(R.id.button_change_password);
        changePasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditPasswordActivity.class);
            startActivity(intent);
        });


    }
    private void savePersonalInfo() {
        String name = userName.getText().toString();
        String birth = userBirth.getText().toString();
        String phone = userPhone.getText().toString();
        String email = userEmail.getText().toString();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("birth", birth);
        values.put("phone", phone);
        values.put("email", email);

        long result = userDB.insert("personal_info", null, values);
        if (result > 0) {
            Toast.makeText(getApplicationContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}

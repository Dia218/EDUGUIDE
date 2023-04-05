package com.capston.eduguide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capston.eduguide.db.DatabaseHelper;
import com.example.bottomnavi.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button signUpButton;

    EditText id;
    EditText password;

    String userId;

    boolean userExit;

    SQLiteDatabase userDB;
    DatabaseHelper helper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new DatabaseHelper(this, "userDB", null, 1);

        loginButton = (Button) findViewById(R.id.btn_login);
        signUpButton = (Button) findViewById(R.id.btn_go_signUp);

        id = (EditText) findViewById(R.id.login_id);
        password = (EditText) findViewById(R.id.login_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userExit = false;

                String isID = id.getText().toString().trim();
                String isPass = password.getText().toString().trim();

                if (isID.length() > 4 && isPass.length() > 4) {
                    searchData(isID, isPass);
                } else {
                    Toast.makeText(LoginActivity.this, "입력이 잘못되었습니다", Toast.LENGTH_SHORT);
                }

                if (userExit) {
                    Intent intent = new Intent();
                    intent.putExtra("userId", userId);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호가" + "없거나 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, 203);
            }
        });
    }

    public void searchData (String isID, String isPass){
        userDB = helper.getReadableDatabase();
        String sql = ("select userId, password from userDB");
        Cursor cursor = userDB.rawQuery(sql, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String id = cursor.getString(0);
            String password = cursor.getString(1);
            if (id.equals(isID) && password.equals(isPass)) {
                userId = id;
                userExit = true;
                break;
            }
        }

    }
}
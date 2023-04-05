package com.capston.eduguide;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.capston.eduguide.db.DatabaseHelper;
import com.example.bottomnavi.R;

public class SignUpActivity extends AppCompatActivity {

    EditText userId;
    EditText userPassword;
    EditText userEmail;
    EditText userPhone;

    SQLiteDatabase userDB;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        helper = new DatabaseHelper(this, "userDB", null, 1);

        userId = (EditText) findViewById(R.id.signUp_id);
        userPassword = (EditText) findViewById(R.id.signUp_password);
        userEmail = (EditText) findViewById(R.id.signUp_email);
        userPhone = (EditText) findViewById(R.id.signUp_phone);

        Button signUpButton = (Button) findViewById(R.id.btn_signUp);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = userId.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String phone = userPhone.getText().toString().trim();

                if(id.length() < 5 || password.length() < 5) {
                    Toast.makeText(SignUpActivity.this,"아이디 5글자 이상\n" + "비밀번호 5글자 이상\n" + "\n입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    insertData(id, password, email, phone);
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }
    public void insertData(String id, String password, String email, String phone) {
        userDB = helper.getWritableDatabase();

        String sql = ("insert into userDB(userId, password, email, phone) values " +
                "(" +  "'" + id + "'" + "," + "'" + password + "'" + "," + "'" + email + "'" + "," + "'" + phone + "'" + ")");

        userDB.execSQL(sql);
    }
}

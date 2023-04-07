package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.login.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    Button button;
    SQLiteDatabase userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dbName = "userDB";
        openDatabase(dbName);

        button = (Button)findViewById(R.id.main_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivityForResult(intent,101);
            }
        });
    }


    public void openDatabase(String dbName) {
        DatabaseHelper helper = new DatabaseHelper(this, dbName, null, 1);
        userDB = helper.getWritableDatabase();
        Toast.makeText(this, "데이터베이스를 열었습니다.", Toast.LENGTH_LONG).show();
    }
}

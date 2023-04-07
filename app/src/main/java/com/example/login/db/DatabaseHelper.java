package com.example.login.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "userDB";
        db.execSQL("CREATE TABLE IF NOT EXISTS " + userTable + " (\n" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "userID TEXT," +
                "password TEXT," +
                "email TEXT," +
                "phone TEXT" + ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 이전 버전에서의 변경 사항에 대한 작업을 수행합니다.
        // 예를 들어, 이전 버전의 테이블을 삭제하고 새로운 버전의 테이블을 만듭니다.
        db.execSQL("DROP TABLE IF EXISTS userDB");
        onCreate(db);
    }
}

package com.capston.eduguide.db;

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
        db.execSQL("create table if not exists " + userTable + " ("
                + " userId integer PRIMARY KEY autoincrement, "
                + " password text, "
                + " email text, "
                + " phone text);");

        //게시글DB 생성
        String postDB = "postTBL";
        db.execSQL("create table if not exists " + postDB + " ("
                + " postId integer PRIMARY KEY autoincrement, "
                + " userId, "
                + " postText text, "
                + " postTag text, "
                + " recommend int,"
                + "FOREIGN KEY(userId) REFERENCES userTable(userId) ON UPDATE CASCADE ON DELETE SET NULL);");

        //가이드툴DB 생성 //가이드툴 = 가이드박스들의 집합
        String guideToolDB = "guideboxTBL";
        db.execSQL("create table if not exists " + guideToolDB + " ("
                + " guideboxId integer PRIMARY KEY autoincrement, "
                + " postId text, "
                + " boxKeyword text, "
                + " boxInfo text,"
                + "FOREIGN KEY(postId) REFERENCES postTBL(postId) ON UPDATE CASCADE ON DELETE CASCADE);");
    }

    public void connectDB(String querySQL) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}

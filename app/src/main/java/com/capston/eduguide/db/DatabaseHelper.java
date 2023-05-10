package com.capston.eduguide.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.capston.eduguide.post.PostItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //사용자DB 생성
        String userDB = "userTBL";
        db.execSQL("CREATE TABLE IF NOT EXISTS " + userDB + " (\n" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "userID TEXT," +
                "password TEXT," +
                "email TEXT," +
                "phone TEXT" + ");");

        //마이페이지용 임시 DB
        String personalDB = "personalTBL";
        db.execSQL("create table if not exists " + personalDB + " ("
                + " _id integer primary key autoincrement, "
                + " name text, "
                + " birth text, "
                + " phone text, "
                + " email text );");

        //게시글DB 생성
        String postDB = "postTBL";
        db.execSQL("create table if not exists " + postDB + " ("
                + " postId integer PRIMARY KEY autoincrement, "
                + " userId, "
                + " postTitle text, "
                + " postText text, "
                + " postTag text, "
                + " recommend integer DEFAULT 0,"
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
        db.execSQL("DROP TABLE IF EXISTS userTBL");
        db.execSQL("DROP TABLE IF EXISTS personalTBL");
        db.execSQL("DROP TABLE IF EXISTS postTBL");
        db.execSQL("DROP TABLE IF EXISTS guideboxTBL");
        onCreate(db);
    }

    public boolean insertUser(String userID, String password, String email, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID",userID);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result = db.insert("userTBL",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertPost(String userID, String postTitle, String postText, String postTag, Integer recommend, Integer save){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",userID);
        contentValues.put("postTitle",postTitle);
        contentValues.put("postText",postText);
        contentValues.put("postTag",postTag);
        contentValues.put("recommend",recommend);
        contentValues.put("save",save);
        long result = db.insert("postTBL",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<PostItem> selectAllFeed(){
        ArrayList<PostItem> result = new ArrayList<PostItem>();
        try{
            Cursor cursor = getReadableDatabase().rawQuery("select userId, postTitle, postText, postTag, recommend from postTBL",null);
            for(int i = 0;i< cursor.getCount();i++){
                cursor.moveToNext();
                String userId = cursor.getString(0);
                String titleStr = cursor.getString(1);
                String textStr = cursor.getString(2);
                String tagStr = cursor.getString(3);
                Integer like_count = cursor.getInt(4);

                PostItem feed = new PostItem();
                feed.setUserId(userId);
                feed.setPostTitle(titleStr);
                feed.setPostInfo(textStr);
                feed.setPostTag(tagStr);
                feed.setLike_count(like_count);
                result.add(feed);
            }
        } catch (Exception ex){
            Log.e("database", "Exception in executing insert SQL.", ex);
        }
        return result;
    }
}

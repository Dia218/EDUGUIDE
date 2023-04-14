package com.capston.eduguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.capston.eduguide.db.TestFirebase;
import com.capston.eduguide.guideTool.GuideTool;
import com.capston.eduguide.login.LoginActivity;
import com.google.android.material.navigation.NavigationBarView;
import android.database.sqlite.SQLiteDatabase;
import com.capston.eduguide.db.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    private NavigationBarView navigationBarView; //하단 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1Feed frag1;
    private Frag2Search frag2;
    private Frag3Posting frag3;
    private Frag4Notice frag4;
    private Frag5User frag5;
    private GuideTool guideTool;

    private static String currentMenu; //현재 메뉴

    private static DatabaseHelper helper; //디비

    TestFirebase testFirebase = new TestFirebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터베이스 생성
        SQLiteDatabase db;
        helper = new DatabaseHelper(com.capston.eduguide.MainActivity.this, "new-db.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //로그인 화면 실행
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        //하단바 뷰
        navigationBarView = findViewById(R.id.bottomNavi);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_feed:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
                        break;
                    case R.id.action_tool:
                        setFrag(2);
                        break;
                    case R.id.action_notification:
                        setFrag(3);
                        break;
                    case R.id.action_mypage:
                        setFrag(4);
                }
                return true;
            }
        });
        frag1 = new Frag1Feed();
        frag2 = new Frag2Search();
        frag3 = new Frag3Posting();
        frag4 = new Frag4Notice();
        frag5 = new Frag5User();
        guideTool = new GuideTool();
        setFrag(0);// 첫 프래그먼트 화면 지정
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                this.setCurrentMenu("feed");
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                this.setCurrentMenu("search");
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                this.setCurrentMenu("posting");
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                this.setCurrentMenu("notice");
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5);
                this.setCurrentMenu("user");
                ft.commit();
                break;

        }
    }
    //프래그먼트 내에서 다른 프래그먼트로 이동하는 메소드
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택
    }

    //현재 메뉴를 설정하는 메소드
    public static void setCurrentMenu(String currentMenu) {
        com.capston.eduguide.MainActivity.currentMenu = currentMenu;
    }

    //현재 메뉴를 반환하는 메소드
    public static String getCurrentMenu() {
        return currentMenu;
    }

    //디비 반환 메소드
    public static DatabaseHelper getHelper() { return helper; }

}
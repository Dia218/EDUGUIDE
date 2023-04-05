package com.capston.eduguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.capston.eduguide.R;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView navigationBarView; //하단 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1Feed frag1;
    private Frag2Search frag2;
    private Frag3Posting frag3;
    private Frag4Notice frag4;
    private Frag5User frag5;
    private Comment comment;
    private Guide guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        comment = new Comment();
        guide = new Guide();
        setFrag(0);// 첫 프래그먼트 화면 지정
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, frag5);
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
}
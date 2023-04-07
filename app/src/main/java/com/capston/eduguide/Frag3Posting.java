package com.capston.eduguide;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.guideTool.GuideTool;

public class Frag3Posting extends Fragment {

    private View view;
    private ViewPager vp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag3_posting, container, false);

        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        AppCompatEditText postTitle = view.findViewById(R.id.postTitle); //제목
        AppCompatEditText postInfo = view.findViewById(R.id.postInfo); //내용
        AppCompatEditText postTag = view.findViewById(R.id.postTag); //태그


        //등록 버튼 이벤트리스너
        view.findViewById(R.id.postingSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //유저 아이디 로직 추가 필요
                Integer pWriterId = 0;
                
                String pTitle = String.valueOf(postTitle.getText()); //제목 받아오기
                String pInfo = String.valueOf(postInfo.getText()); //내용 받아오기
                String pTag = String.valueOf(postTag.getText()); //태그 받아오기

                //게시글DB 등록
                SQLiteDatabase db = com.capston.eduguide.MainActivity.getHelper().getWritableDatabase();
                db.execSQL("INSERT INTO postTBL VALUES ("
                        + pWriterId + ", '"
                        + pTitle + "', '"
                        + pInfo + "', '"
                        + pTag + "', "
                        + 0 + ");");
                db.close();

                //가이드툴DB 등록 메소드 호출 필요

            }
        });

        return view;
    }

    private class BannerPagerAdapter extends FragmentPagerAdapter {

        public BannerPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return GuideTool.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

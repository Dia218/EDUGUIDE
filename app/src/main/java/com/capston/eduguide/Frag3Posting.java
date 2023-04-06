package com.capston.eduguide;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.db.DatabaseHelper;

import org.w3c.dom.Text;

public class Frag3Posting extends Fragment {

    private View view;
    private ViewPager vp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag3, container, false);

        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        Text postTitle = view.findViewById(R.id.postTitle); //제목

        Text postInfo = view.findViewById(R.id.postInfo); //설명

        Text postTag = view.findViewById(R.id.postTag); //태그

        //등록 버튼 이벤트리스너
        view.findViewById(R.id.postingSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postTitle.getWholeText();
                postInfo.getWholeText();
                postTag.getWholeText();


                DatabaseHelper db = MainActivity.getHelper();
                db.execSQL("??");

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
            return Guide.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

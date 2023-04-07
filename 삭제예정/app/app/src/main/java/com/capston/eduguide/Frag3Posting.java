package com.capston.eduguide;

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

import com.example.bottomnavi.R;

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

package com.capston.eduguide.post;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.capston.eduguide.guideTool.GuideTool;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(Fragment fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return GuideTool.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
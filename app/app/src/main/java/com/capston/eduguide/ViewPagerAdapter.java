package com.capston.eduguide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(Fragment fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return Guide.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

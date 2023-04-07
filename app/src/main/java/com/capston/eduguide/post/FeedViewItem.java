package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.capston.eduguide.guideTool.GuideTool;

import java.util.ArrayList;

public class FeedViewItem {
    //private Drawable iconDrawable ;
    private Drawable userIcon;
    private String titleStr ;
    private String descStr ;
    private String tagStr;
    private String username;
    private String like_count;
    private String bookmark_count;
    private Integer grade;
    private BannerPagerAdapter viewPager;


    //public void setIcon(Drawable icon) { iconDrawable = icon;}
    public void setUserIcon(Drawable icon) {
        userIcon = icon;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDesc(String desc) {
        descStr = desc;
    }
    public void setTag(String tag){ tagStr = tag; }
    public void setUsername(String name) {
        username = name;
    }
    public void setLike_count(String count) { like_count = count; }
    public void setBookmark_count(String count) { bookmark_count = count; }
    public void setGrade(Integer grade) { this.grade = grade; }
    public void setViewPager(BannerPagerAdapter bpa) { this.viewPager = bpa; }


    //public Drawable getIcon() { return this.iconDrawable ;}
    public Drawable getUserIcon() {
        return this.userIcon ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getTag(){ return this.tagStr; }
    public String getUsername() {
        return this.username ;
    }
    public String getLike_count() {
        return this.like_count ;
    }
    public String getBookmark_count(){
        return this.bookmark_count ;
    }
    public Integer getGrade() { return this.grade; }
    public BannerPagerAdapter getViewPager() { return this.viewPager; }



    private class BannerPagerAdapter extends FragmentStatePagerAdapter {

        //Guide guide = new Guide();
        public BannerPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            //Guide guide = Guide.newInstance(position);
            GuideTool guide = new GuideTool();
            Bundle bundle = new Bundle();
            ArrayList<String> key = new ArrayList<>();
            for(int i=1;i<15;i++){
                key.add("keyword"+i);
            }
            bundle.putInt("guideboxsize-1",12);
            bundle.putInt("guidelinesize",9);
            bundle.putStringArrayList("key",key);
            guide.setArguments(bundle);
            return guide;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

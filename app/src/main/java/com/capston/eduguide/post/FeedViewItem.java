package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.capston.eduguide.guideTool.GuideFragment;

import java.util.ArrayList;

public class FeedViewItem {
    private String feedId;
    private Drawable userIcon;
    private String titleStr;
    private String textStr;
    private String tagStr;
    private String userName;
    private Integer like_count;
    private Integer bookmark_count;
    private Integer grade;
    private BannerPagerAdapter viewPagerAdapter;


    public void setFeedId(String id) {
        feedId = id;
    }

    public void setUserIcon(Drawable icon) {
        userIcon = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setText(String text) {
        textStr = text;
    }
    public void setTag(String tag){ tagStr = tag; }
    public void setUserName(String name) {
        userName = name;
    }

    public void setLike_count(Integer count) {
        like_count = count;
    }

    public void setBookmark_count(Integer count) {
        bookmark_count = count;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setViewPagerAdapter(BannerPagerAdapter bpa) {
        this.viewPagerAdapter = bpa;
    }

    public String getFeedId() {
        return this.feedId;
    }

    public Drawable getUserIcon() {
        return this.userIcon;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getText() {
        return this.textStr;
    }
    public String getTag(){ return this.tagStr; }
    public String getUserName() { return this.userName ;}
    public Integer getLike_count() {
        return this.like_count;
    }

    public Integer getBookmark_count() {
        return this.bookmark_count;
    }

    public Integer getGrade() {
        return this.grade;
    }
    public BannerPagerAdapter getViewPagerAdapter() {
        return this.viewPagerAdapter;
    }

    public static class BannerPagerAdapter extends FragmentPagerAdapter {

        GuideFragment guide = new GuideFragment();
        public Integer adapterId;

        public BannerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Guide guide = Guide.newInstance(position);
            //Guide guide = new Guide();
            return guide;
        }

        public void getGuide(String postId) {
            setAdapterId(Integer.parseInt(postId));
            guide.setGuide(postId);
        }

        @Override
        public int getCount() {
            return 1;
        }

        public Integer getAdapterId(){
            return this.adapterId;
        }

        public void setAdapterId(int position){
            this.adapterId = position;
        }

    }
}

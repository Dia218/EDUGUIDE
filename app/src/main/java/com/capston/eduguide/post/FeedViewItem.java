package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;

import androidx.viewpager2.widget.ViewPager2;

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
    private ViewPager2 viewPager;


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
    public void setViewPager(ViewPager2 vp) { this.viewPager = vp; }


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
    public ViewPager2 getViewPager() { return this.viewPager; }
}

package com.capston.eduguide;

import android.graphics.drawable.Drawable;

public class FeedViewItem {
    //private Drawable iconDrawable ;
    private Drawable userIcon;
    private String titleStr ;
    private String descStr ;
    private String username;
    private String like_count;
    private String bookmark_count;
    private Integer grade;


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
    public void setUsername(String name) {
            username = name;
    }
    public void setLike_count(String count) { like_count = count; }
    public void setBookmark_count(String count) { bookmark_count = count; }
    public void setGrade(Integer grade) { this.grade = grade; }

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
    public String getUsername() {
        return this.username ;
    }
    public String getLike_count() {
        return this.like_count ;
    }
    public String getBookmark_count(){
        return this.bookmark_count ;
    }
    public Integer getGrade() { return grade; }
}

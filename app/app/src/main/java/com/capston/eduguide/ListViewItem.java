package com.capston.eduguide;

import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ListViewItem {
    private Drawable iconDrawable ;
    private Drawable userIcon;
    private String titleStr ;
    private String descStr ;
    private String username;

    public void setIcon(Drawable icon) { iconDrawable = icon ;}
    public void setUserIcon(Drawable icon) {
        userIcon = icon;
    }
    public void setTitle(String title) {
            titleStr = title ;
    }
    public void setDesc(String desc) {
            descStr = desc ;
    }
    public void setUsername(String name) {
            username = name ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
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
}

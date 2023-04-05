package com.capston.eduguide;

import android.graphics.drawable.Drawable;

public class CommentItem {

    private Drawable userIcon;
    private String commentStr ;
    private String username;


    public void setUserIcon(Drawable icon) {
        userIcon = icon;
    }
    public void setComment(String comment) {
        commentStr = comment ;
    }
    public void setUsername(String name) { username = name ; }

    public Drawable getUserIcon() {
        return this.userIcon ;
    }
    public String getComment() {
        return this.commentStr ;
    }
    public String getUsername() {
        return this.username ;
    }

}

package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class CommentItem {

    //private Drawable userIcon;
    private String commentStr ;
    private String username;
    //private ArrayList<ReplyItem_unfinished> replyList;

    public CommentItem(){

    }

    public CommentItem(String commentStr, String username){
        //this.userIcon = userIcon;
        this.commentStr = commentStr;
        this.username = username;
    }

    //public void setUserIcon(Drawable icon) { userIcon = icon;}
    public void setComment(String comment) {
        commentStr = comment ;
    }
    public void setUsername(String name) { username = name ; }
    /*public void setReplyList(ArrayList<ReplyItem_unfinished> replyList){
        this.replyList = replyList;
    }*/

    //public Drawable getUserIcon() { return this.userIcon ;}
    public String getComment() {
        return this.commentStr ;
    }
    public String getUsername() {
        return this.username ;
    }
    //public ArrayList<ReplyItem_unfinished> getReplyList(){ return this.replyList ; }

}

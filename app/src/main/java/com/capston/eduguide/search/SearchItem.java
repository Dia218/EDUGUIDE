package com.capston.eduguide.search;

public class SearchItem{
    private String postId;
    private String title;
    private String tag;

    public SearchItem(String postId,String title,String tag){
        this.postId=postId;
        this.title=title;
        this.tag=tag;
    }
    public String getPostId(){return postId;}
    public String getTitle(){
        return title;
    }
    public String getTag(){
        return tag;
    }
}








package com.capston.eduguide.search;

public class SearchItem{
    private String postId;
    private String title;
    private String tag;
    private String description;
    private String userId;

    public SearchItem(String postId,String title,String tag,String description,String userId){
        this.postId=postId;
        this.title=title;
        this.tag=tag;
        this.description=description;
        this.userId=userId;
    }
    public String getPostId(){return postId;}
    public String getTitle(){
        return title;
    }
    public String getTag(){
        return tag;
    }
    public String getDescription(){return description;}
    public String getUserId(){return userId;}
}
package com.capston.eduguide.search;

public class SearchItem{
    private String title;
    private String tag;

    public SearchItem(String title,String tag){
        this.title=title;
        this.tag=tag;
    }
    public String getTitle(){
        return title;
    }
    public String getTag(){
        return tag;
    }
}


package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.capston.eduguide.guideTool.GuideTool;

import java.util.ArrayList;

public class PostItem {
    //private Drawable iconDrawable ;
    private Integer postId; //게시글 번호
    private Drawable userIcon; //사용자 아이콘 or 뱃지
    private String postTitle; //게시글 제목
    private String postInfo; //게시글 설명
    private String postTag; //게시글 태그
    private String userId; //작성자
    private Integer like_count; //추천 수
    private Integer bookmark_count; //북마크 수
    private Integer grade; //??
    //private ArrayList<CommentItem> comments;
    //private ArrayList<String> comment;
    private BannerPagerAdapter viewPagerAdapter;


    //public void setIcon(Drawable icon) { iconDrawable = icon;}
    public void setPostId(Integer id) { postId = id; }
    public void setUserIcon(Drawable icon) {
        userIcon = icon;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public void setPostInfo(String postInfo) {
        this.postInfo = postInfo;
    }
    public void setPostTag(String postTag){ this.postTag = postTag; }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setLike_count(Integer count) { like_count = count; }
    public void setBookmark_count(Integer count) { bookmark_count = count; }
    public void setGrade(Integer grade) { this.grade = grade; }
    //public void setComment(ArrayList<CommentItem> comment) { this.comments = comment; }
    //public void setComment(ArrayList<String> comment) { this.comment = comment; }
    public void setViewPagerAdapter(BannerPagerAdapter bpa) { this.viewPagerAdapter = bpa; }


    //public Drawable getIcon() { return this.iconDrawable ;}
    public Integer getPostId() { return this.postId; }
    public Drawable getUserIcon() {
        return this.userIcon ;
    }
    public String getPostTitle() {
        return this.postTitle;
    }
    public String getPostInfo() {
        return this.postInfo;
    }
    public String getPostTag(){ return this.postTag; }
    public String getUserId() { return this.userId ;}
    public Integer getLike_count() {
        return this.like_count ;
    }
    public Integer getBookmark_count(){
        return this.bookmark_count ;
    }
    public Integer getGrade() { return this.grade; }
    //public ArrayList<CommentItem> getComment() { return this.comments; }
    //public ArrayList<String> getComment() { return this.comment; }
    public BannerPagerAdapter getViewPagerAdapter() { return this.viewPagerAdapter; }

    //public void addComments(CommentItem comm) { comments.add(comm); }
    //public void addComment(String comm) { comment.add(comm); }

    public static class BannerPagerAdapter extends FragmentPagerAdapter {

        GuideTool guide = new GuideTool();
        public BannerPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Guide guide = Guide.newInstance(position);
            //Guide guide = new Guide();
            return guide;
        }

        public void getGuide(int boxSize){
            Bundle bundle = new Bundle();
            ArrayList<String> key = new ArrayList<>();
            for(int i=0;i<15;i++){
                key.add("keyword"+i);
            }
            bundle.putInt("guideboxsize",boxSize);
            bundle.putInt("guidelinesize",9);
            bundle.putStringArrayList("key",key);
            guide.setArguments(bundle);

        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

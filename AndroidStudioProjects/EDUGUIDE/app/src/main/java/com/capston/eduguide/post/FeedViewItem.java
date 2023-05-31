package com.capston.eduguide.post;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.capston.eduguide.guideTool.GuideFragment;

import java.util.ArrayList;

public class FeedViewItem {
    //private Drawable iconDrawable ;
    private String feedId;
    private Drawable userIcon;
    private String titleStr;
    private String textStr;
    private String tagStr;
    private String userId;
    private Integer like_count;
    private Integer bookmark_count;
    private Integer grade;
    private BannerPagerAdapter viewPagerAdapter;


    //public void setIcon(Drawable icon) { iconDrawable = icon;}
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

    public void setTag(String tag) {
        tagStr = tag;
    }

    public void setUserId(String name) {
        userId = name;
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

    //public void setComment(ArrayList<CommentItem> comment) { this.comments = comment; }
    //public void setComment(ArrayList<String> comment) { this.comment = comment; }
    public void setViewPagerAdapter(BannerPagerAdapter bpa) {
        this.viewPagerAdapter = bpa;
    }


    //public Drawable getIcon() { return this.iconDrawable ;}
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

    public String getTag() {
        return this.tagStr;
    }

    public String getUserId() {
        return this.userId;
    }

    public Integer getLike_count() {
        return this.like_count;
    }

    public Integer getBookmark_count() {
        return this.bookmark_count;
    }

    public Integer getGrade() {
        return this.grade;
    }

    //public ArrayList<CommentItem> getComment() { return this.comments; }
    //public ArrayList<String> getComment() { return this.comment; }
    public BannerPagerAdapter getViewPagerAdapter() {
        return this.viewPagerAdapter;
    }

    //public void addComments(CommentItem comm) { comments.add(comm); }
    //public void addComment(String comm) { comment.add(comm); }

    public static class BannerPagerAdapter extends FragmentPagerAdapter {

        GuideFragment guide = new GuideFragment();

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
            guide.setGuide(postId);

            /* 현수가 짰던 코드
            Bundle bundle = new Bundle();
            ArrayList<String> key = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                key.add("keyword" + i);
            }
            bundle.putInt("guideboxsize", boxSize);
            bundle.putInt("guidelinesize", 9);
            bundle.putStringArrayList("key", key);
            guide.setArguments(bundle);*/
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
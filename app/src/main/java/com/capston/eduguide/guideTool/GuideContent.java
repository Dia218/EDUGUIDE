package com.capston.eduguide.guideTool;

import java.util.List;
import java.util.Vector;


public class GuideContent {
    private String postId; // 게시글 아이디
    private List<GuideBox> guideBoxList; // 가이드 박스 리스트

    public GuideContent() {
        // Default constructor required for Firebase
    }

    public GuideContent(String postId, List<GuideBox> guideBoxList) {
        this.postId = postId;
        this.guideBoxList = guideBoxList;
    }

    // Getter and setter methods

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<GuideBox> getGuideBoxList() {
        return guideBoxList;
    }

    public void setGuideBoxList(List<GuideBox> guideBoxList) {
        this.guideBoxList = guideBoxList;
    }
}

class GuideBox {
    private String keyword; // 박스 키워드 (박스 전면에 표시)
    private String boxInfo; // 박스 설명 (박스 팝업 창에 표시)

    public GuideBox() {
        // Default constructor required for Firebase
    }

    public GuideBox(String keyword, String boxInfo) {
        this.keyword = keyword;
        this.boxInfo = boxInfo;
    }

    // Getter and setter methods

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBoxInfo() {
        return boxInfo;
    }

    public void setBoxInfo(String boxInfo) {
        this.boxInfo = boxInfo;
    }
}
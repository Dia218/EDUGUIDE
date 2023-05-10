package com.capston.eduguide.guideTool;

import java.util.List;
import java.util.Vector;

public class GuideContent {
    private String postId; //게시글 아이디
    private Vector<GuideBox> guideBoxVector; //가이드 박스 벡터

    public GuideContent() {
        // Default constructor required for Firebase
    }

    public GuideContent(String postId, Vector<GuideBox> guideBoxVector) {
        this.postId = postId;
        this.guideBoxVector = guideBoxVector;
    }

    // Getter and setter methods
}
class GuideBox {
    private String keyword; //박스 키워드 (박스 전면에 표시)
    private String boxInfo; //박스 설명 (박스 팝업 창에 표시)

    public GuideBox() {
        // Default constructor required for Firebase
    }

    public GuideBox(String keyword, String boxInfo) {
        this.keyword = keyword;
        this.boxInfo = boxInfo;
    }

    // Getter and setter methods
}
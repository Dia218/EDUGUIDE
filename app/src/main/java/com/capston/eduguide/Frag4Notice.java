package com.capston.eduguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag4Notice extends Fragment {

    private View view;

    private TextView noticeText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4_notice, container, false);

        return view;
    }

    //알림 내용을 보여주는 메소드
    public void showNotification(int caseType, String title, String userId) {

        String notificationText = "";

        switch (caseType) {
            case 0://댓글 알림
                notificationText = String.format("'%s' 게시글에 새로운 댓글이 달렸습니다.", title);
                break;
            case 1://추천 알림
                notificationText = String.format("'%s' 게시글이 추천되었습니다. ", title);
                break;
            case 2://스크랩 알림
                notificationText = String.format("'%s' 게시글이 스크랩되었습니다.", title);
                break;
            case 3://뱃지 알림
                notificationText = String.format("'%s'님에게 새로운 뱃지가 생겼습니다!", userId);
                break;
        }
        noticeText.setText(notificationText);
    }
}

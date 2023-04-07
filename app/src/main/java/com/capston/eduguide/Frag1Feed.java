package com.capston.eduguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capston.eduguide.feed.ListViewAdapter;
import com.capston.eduguide.feed.ListViewItem;
import com.capston.eduguide.guideTool.GuideTool;

import java.util.ArrayList;

public class Frag1Feed extends Fragment {

    ArrayList<ListViewItem> items;
    private GuideTool fragmentGuideTool;
    private SwipeRefreshLayout swipeRefreshLayout;
    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성.
    public static Frag1Feed newInstance(){
        return new Frag1Feed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.frag1_feed, container, false);

        // 리스트 뷰 참조 및 Adapter 달기
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerGuide);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정
        ListViewAdapter adapter = new ListViewAdapter(getChildFragmentManager(),getContext()) ;
        recyclerView.setAdapter(adapter);

        //아이템 추가
        adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.post_person,null),
                "사용자 아이디1","본문 내용 표시\n그리고 내용 추가\n내용 잘리는지 확인\n\n\n\n","#tag1","0","0");
        adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.post_person,null),
                "name2","desc2","tag2","12","0");
        adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.post_person,null),
                "name3","desc3","tag3","0","4");

        swipeRefreshLayout = rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //swipe 시 수행할 동작
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Frag1Feed.this).attach(Frag1Feed.this).commit();
                //업데이트 끝남.필수
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }
}



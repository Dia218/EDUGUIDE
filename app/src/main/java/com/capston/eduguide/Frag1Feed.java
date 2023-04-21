package com.capston.eduguide;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.capston.eduguide.db.DatabaseHelper;
import com.capston.eduguide.guideTool.GuideTool;
import com.capston.eduguide.post.FeedViewAdapter;
import com.capston.eduguide.post.FeedViewItem;
import com.capston.eduguide.post.onDatabaseCallback;

import java.util.ArrayList;

public class Frag1Feed extends Fragment implements onDatabaseCallback {

    private GuideTool fragmentGuide;
    DatabaseHelper helper;
    FeedViewAdapter adapter;
    ArrayList<FeedViewItem> items;
    private SwipeRefreshLayout swipeRefreshLayout;
    Parcelable recyclerViewState;
    RecyclerView recyclerView;
    // 각각의 Fragment마다 Instance를 반환해 줄 메소드를 생성.
    /*public static Frag1Feed newInstance(){
        return new Frag1Feed();
    }*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.frag1_feed, container, false);
            //rootView = inflater.inflate(R.layout.frag1_feed, container, false);
            //rootView = inflater.inflate(R.layout.post_feedview_item,container,false);
            // 리스트 뷰 참조 및 Adapter 달기
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerGuide);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SQLiteDatabase myDb;
        helper = MainActivity.getHelper();

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정
        adapter = new FeedViewAdapter(getChildFragmentManager(), getContext());
        recyclerView.setAdapter(adapter);
        items = selectAllFeed();
        adapter.setItems(items);

        //아이템 추가
        //adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
        //        ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null),
        //        "사용자 아이디1", "피드 제목 표시1", "메인 내용 표시할 거1", "#tag1", 0, "0", 10);
        //adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
        //        ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null),
        //        "name2", "Title2", "메인 내용 표시할 거1", "tag2", 2, "0", 12);
        //adapter.addItem(/*ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),*/
        //        ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null),
        //        "name3", "Title3", "메인 내용 표시할 거1", "tag3", 0, "4", 15);

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

    @Override
    public ArrayList<FeedViewItem> selectAllFeed() {
        ArrayList<FeedViewItem> result = helper.selectAllFeed();
        //가이드 툴 추가는 여기서(가이드 툴 db 받으면 게시글 db와 비교로 가져와서 넣기), 뱃지는 등급-팀원들과 상의 필요
        for(int i=0;i<result.size();i++){
            FeedViewItem item = result.get(i);
            FeedViewItem.BannerPagerAdapter bpa = new FeedViewItem.BannerPagerAdapter(adapter.getFm());
            bpa.getGuide(12);
            item.setViewPagerAdapter(bpa);
            item.setGrade(10);
            setUserIconForGrade(item);
            result.set(i,item);
        }
        return result;
    }

    public void setUserIconForGrade(FeedViewItem item){
        if(item.getGrade()<10){
            item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null));
        }
        else if (item.getGrade()<20) {
            item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null));
        }
        else if (item.getGrade()<30) {
            item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null));
        }
        else if (item.getGrade()<40) {
            item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null));
        }
    }
}



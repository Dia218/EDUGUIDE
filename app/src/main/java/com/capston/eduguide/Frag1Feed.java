package com.capston.eduguide;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import com.capston.eduguide.guideTool.GuideTool;
import com.capston.eduguide.post.FeedViewAdapter;
import com.capston.eduguide.post.FeedViewItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag1Feed extends Fragment {

    private GuideTool fragmentGuide;
    FeedViewAdapter adapter;
    ArrayList<FeedViewItem> items;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ValueEventListener mListener;
    RecyclerView recyclerView;
    Parcelable recyclerViewState = null;
    //RecyclerView recyclerView;
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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerGuide);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("post");
        mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FeedViewItem item = snapshot.getValue(FeedViewItem.class);
                    items.add(item);
                }
                addGuide();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(mListener);

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정
        adapter = new FeedViewAdapter(getChildFragmentManager(), getActivity());
        adapter.setItems(items);
        recyclerView.setAdapter(adapter);

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
    public void onStart() {
        super.onStart();
        Log.i("onStart","check Start!");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("onStart","check Start!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume","check Resume!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("onStop","check Stop!");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause", "check Pause!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView", "check DestroyView!");
        databaseReference.removeEventListener(mListener);
    }


    public void addGuide() {
        //ArrayList<FeedViewItem> result = items;
        //가이드 툴 추가는 여기서(가이드 툴 db 받으면 게시글 db와 비교로 가져와서 넣기), 뱃지는 등급-팀원들과 상의 필요
        for(int i=0;i<items.size();i++){
            FeedViewItem item = items.get(i);
            FeedViewItem.BannerPagerAdapter bpa = new FeedViewItem.BannerPagerAdapter(adapter.getFm());
            bpa.getGuide(12);
            item.setViewPagerAdapter(bpa);
            item.setGrade(10);
            setUserIconForGrade(item);
            items.set(i,item);
        }
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

    private void saveRecyclerViewState() {
        // LayoutManager를 불러와 Parcelable 변수에 리사이클러뷰 상태를 Bundle 형태로 저장한다
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    private void setSavedRecyclerViewState() {
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
    };
}



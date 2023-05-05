package com.capston.eduguide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.search.SearchAdapter;
import com.capston.eduguide.search.SearchItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Frag2Search extends Fragment {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private SearchAdapter adapter;
    public Frag2Search() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2_search, container, false);

        //firebase 초기화
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //recyclerView 초기화
        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new SearchAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        //searchView 초기화
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                search(newText);
                return true;
            }
        });
        return view;
    }
    private void search(String query){
        //firebase에서 검색어와 일치하는 태그값을 찾아 검색결과를 가져옴

        databaseReference.child("posts")
                .orderByChild("tag")
                .startAt(query)
                .endAt(query+"\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                        List<SearchItem> searchItems=new ArrayList<>();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            //태그가 일치하는 경우
                            String tag = snapshot.child("tag").getValue(String.class);
                            if(tag!=null&& tag.contains(query)){
                                String title=snapshot.child("title").getValue(String.class);
                                String postId=snapshot.getKey();
                                SearchItem searchItem=new SearchItem(postId,title,tag);
                                searchItems.add(searchItem);
                            }
                        }
                        adapter.setSearchItems(searchItems);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError){
                        Log.e("Frag2Search","onCancelled",databaseError.toException());
                    }
                });
    }
}

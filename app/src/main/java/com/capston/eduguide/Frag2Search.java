package com.capston.eduguide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.db.DatabaseHelper;
import com.capston.eduguide.post.CommentSimple;
import com.capston.eduguide.search.SearchAdapter;
import com.capston.eduguide.search.SearchItem;

import java.util.ArrayList;

public class Frag2Search extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private SearchAdapter adapter;

    private ArrayList<SearchItem> searchlist;

    private DatabaseHelper dbhelper;

    public Frag2Search() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2_search, container, false);

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        dbhelper = MainActivity.getHelper();

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //검색어 완료시 호출되는 메소드
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && gestureDetector.onTouchEvent(motionEvent)) {
                    int position = recyclerView.getChildAdapterPosition(child);
                    SearchItem searchItem = searchlist.get(position);

                    // CommentSimple 클래스 이동하는 intent를 생성하고 데이터 전달
                    Intent intent = new Intent(getContext(), CommentSimple.class);
                    intent.putExtra("title", searchItem.getTitle());
                    intent.putExtra("tag", searchItem.getTag());
                    startActivity(intent);

                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        return view;
    }

    private void search(String searchText) {
        //SQLite에서 검색어와 일치하는 데이터를 찾아서 Arraylist에 추가
        searchlist = new ArrayList<SearchItem>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM postTBL WHERE postTag='" + searchText + "'", null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(2);
                String tag = cursor.getString(4);
                searchlist.add(new SearchItem(title, tag));
            } while (cursor.moveToNext());
        }
        adapter = new SearchAdapter(searchlist);
        recyclerView.setAdapter(adapter);
    }
}

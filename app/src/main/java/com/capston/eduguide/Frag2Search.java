package com.capston.eduguide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.db.DatabaseHelper;

import java.util.ArrayList;

public class Frag2Search extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private SearchAdapter adapter;

    private ArrayList<SearchItem> searchlist;

    private DatabaseHelper dbhelper;

    public Frag2Search(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag2_search, container, false);

        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        dbhelper = MainActivity.getHelper();

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                //검색어 완료시 호출되는 메소드
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                search(newText);
                return false;
            }
        });

        return view;
    }
    private void search(String searchText){
        //SQLite에서 검색어와 일치하는 데이터를 찾아서 Arraylist에 추가
        searchlist = new ArrayList<SearchItem>();
        SQLiteDatabase db=dbhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM postTBL WHERE tag='" + searchText + "'", null);

        if(cursor.moveToFirst()){
            do{
                String title=cursor.getString(2);
                String tag=cursor.getString(4);
                searchlist.add(new SearchItem(title,tag));
            }while (cursor.moveToNext());
        }
        adapter = new SearchAdapter(searchlist);
        recyclerView.setAdapter(adapter);
    }
}

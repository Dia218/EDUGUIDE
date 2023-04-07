package com.capston.eduguide;

import androidx.fragment.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
public class Frag2Activity extends Fragment {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private SearchAdapter adapter;

    private ArrayList<SearchItem> searchlist;

    private DBHelper dbhelper;

    public Frag2Activity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag2, container, false);

        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        dbhelper=new DBHelper(getContext());

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
              //검색어 완료시 호출되는 메소드
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
        //SQLite에서 검색어와 일ㄹ치하는 데이터를 찾아서 Arraylist에 추가
        searchlist = new ArrayList<SearchItem>();
        SQLiteDatabase db=DBHelper.getWriteableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM mytable WHERE tag='" + searchText + "'", null);

        if(cursor.moveToFirst()){
            do{
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String tag=cursor.getString(cursor.getColumnIndex("tag"));
                searchlist.add(new SearchItem(title,tag));
            }while (cursor.movetoNext());
        }
        adapter = new SearchAdapter(searchlist);
        recyclerView.setAdapter(adapter);
    }
}

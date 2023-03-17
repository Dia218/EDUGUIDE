package com.capston.eduguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.bottomnavi.R;

import java.util.ArrayList;

public class Frag1Activity extends Fragment {

    ArrayList<ListViewItem> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.frag1, container, false);

        ListView listView;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter(getContext()) ;

        // 리스트 뷰 참조 및 Adapter 달기
        listView = (ListView) rootView.findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        //아이템 추가
        adapter.addItem(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),
                "name1","desc1","tag1");
        adapter.addItem(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),
                "name2","desc2","tag2");
        adapter.addItem(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.test,null),
                ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),
                "name3","desc3","tag3");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                String usernameStr = item.getUsername();
                Drawable guideDrawable = item.getIcon();
                Drawable userIconDrawable = item.getUserIcon();

                // TODO : use item data.
                

            }
        });
        return rootView;
    }
}



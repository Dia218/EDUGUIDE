package com.capston.eduguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bottomnavi.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    class ViewHolder {
        public TextView username;
        public TextView guidetext;
        public TextView tagtext;
        public ImageView iconImage;
        public ImageView userImage;
    }

    // ListViewAdapter의 생성자
    public ListViewAdapter(Context context){
        this.context = context;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.username = (TextView) convertView.findViewById(R.id.userName);
        viewHolder.guidetext = (TextView) convertView.findViewById(R.id.guideDesc);
        viewHolder.tagtext = (TextView) convertView.findViewById(R.id.tag);
        viewHolder.iconImage = (ImageView) convertView.findViewById(R.id.guideImage);
        viewHolder.userImage = (ImageView) convertView.findViewById(R.id.userImage);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem item = listViewItemList.get(position);
        viewHolder.username.setText(item.getUsername());
        viewHolder.guidetext.setText(item.getTitle());
        viewHolder.tagtext.setText(item.getDesc());
        Glide
                .with(context)
                .load(item.getUserIcon())
                .apply(new RequestOptions().override(50,50))
                .into(viewHolder.userImage);
        Glide
                .with(context)
                .load(item.getIcon())
                .into(viewHolder.iconImage);


        //Return the completed view to render on screen
        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(Drawable icon, Drawable userIcon, String username, String title, String desc) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setUserIcon(userIcon);
        item.setUsername(username);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }
}

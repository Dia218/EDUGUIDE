package com.capston.eduguide;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bottomnavi.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private Context context;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    private FragmentManager fm;

    // ListViewAdapter의 생성자
    public ListViewAdapter(FragmentManager fm, Context context){
        this.context = context;
        this.fm = fm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView guideText;
        public TextView tagText;
        //public ImageView iconImage;
        public ImageView userImage;
        public ViewPager vp;

        ViewHolder(View itemView){
            super(itemView);
            // 뷰 객체에 대한 참조.
            username = itemView.findViewById(R.id.userName);
            guideText = itemView.findViewById(R.id.guideDesc);
            tagText = itemView.findViewById(R.id.tag);
            //iconImage = itemView.findViewById(R.id.guideImage);
            userImage = itemView.findViewById(R.id.userImage);
            vp = (ViewPager) itemView.findViewById(R.id.vp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        ListViewItem item = listViewItemList.get(pos);
                        String titleStr = item.getTitle() ;
                        String descStr = item.getDesc() ;
                        String usernameStr = item.getUsername();
                        //Drawable guideDrawable = item.getIcon();
                        Drawable userIconDrawable = item.getUserIcon();

                        // TODO : use item data.
                        Bundle bundle = new Bundle();
                        bundle.putString("main_text",titleStr);
                        bundle.putString("tag_text",descStr);
                        bundle.putString("user_name",usernameStr);
                        //bundle.putBundle("user_icon",guideDrawable);
                        //bundle.putBundle("user_icon",(Bundle) userIconDrawable);
                        Comment comment = new Comment();
                        comment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,comment).addToBackStack(null).commit();

                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음
    ListViewAdapter(ArrayList<ListViewItem> list){
        listViewItemList = list;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.listview_item, parent, false);
        ListViewAdapter.ViewHolder vh = new ListViewAdapter.ViewHolder(view);
        return vh;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListViewItem item = listViewItemList.get(position);
        holder.username.setText(item.getUsername());
        holder.guideText.setText(item.getTitle());
        holder.tagText.setText(item.getDesc());
        Glide
                .with(context)
                .load(item.getUserIcon())
                .apply(new RequestOptions().override(50,50))
                .into(holder.userImage);
        //viewHolder.guide
        /*Glide
                .with(context)
                .load(item.getIcon())
                .into(holder.iconImage);*/

        BannerPagerAdapter bpa = new BannerPagerAdapter(fm);
        holder.vp.setAdapter(bpa);
        holder.vp.setId(position+1);

    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(/*Drawable icon,*/ Drawable userIcon, String username, String title, String desc) {
        ListViewItem item = new ListViewItem();

        //item.setIcon(icon);
        item.setUserIcon(userIcon);
        item.setUsername(username);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    private class BannerPagerAdapter extends FragmentPagerAdapter{

        public BannerPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return Guide.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

package com.capston.eduguide.post;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<PostItem> postItems = new ArrayList<PostItem>() ;
    private FragmentManager fm;
    private int position;

    private ValueEventListener mListener;

    // ListViewAdapter의 생성자
    public PostAdapter(FragmentManager fm, Context context){
        this.context = context;
        this.fm = fm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView titleText;
        public TextView tagText;
        public ImageView userImage;
        public Button like;
        //public Button delete;
        public TextView like_count;
        public Button bookmark;
        public TextView bookmark_count;
        public ViewPager vp;
        public PostItem.BannerPagerAdapter bpa;
        public Integer userGrade;
        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        private DatabaseReference databaseReference = database.getReference("post");


        ViewHolder(View itemView){
            super(itemView);
            // 뷰 객체에 대한 참조.
            like = itemView.findViewById(R.id.like_button);
            like_count = itemView.findViewById(R.id.like_count);
            bookmark = itemView.findViewById(R.id.bookmark_button);
            bookmark_count = itemView.findViewById(R.id.bookmark_count);
            //delete = itemView.findViewById(R.id.delete_feed);
            username = itemView.findViewById(R.id.userName);
            titleText = itemView.findViewById(R.id.guideTitle);
            tagText = itemView.findViewById(R.id.tag);
            //iconImage = itemView.findViewById(R.id.guideImage);
            userImage = itemView.findViewById(R.id.feedUserImage);
            vp = (ViewPager) itemView.findViewById(R.id.vp);
            userGrade = 10;
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    String pos = Integer.toString(getAdapterPosition());
                    if(v.isSelected()){
                        int count = Integer.parseInt(like_count.getText().toString());
                        count += 1;
                        like_count.setText(Integer.toString(count));
                        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference("post");
                        databaseReference.child(pos).child("like_count").setValue(count);*/
                    }
                    else{
                        int count = Integer.parseInt(like_count.getText().toString());
                        if(count != 0){
                            count -= 1;
                            like_count.setText(Integer.toString(count));
                            /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference("post");
                            databaseReference.child(pos).child("like_count").setValue(count);*/
                        }
                    }
                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected());
                    if(v.isSelected()){
                        int count = Integer.parseInt(bookmark_count.getText().toString());
                        bookmark_count.setText(Integer.toString(++count));
                    }
                    else{
                        int count = Integer.parseInt(bookmark_count.getText().toString());
                        if(count != 0){
                            bookmark_count.setText(Integer.toString(--count));
                        }
                    }
                }
            });

            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(position);
                    removeItem(position);
                }
            });*/

            titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        PostItem item = postItems.get(pos);
                        String titleStr = item.getPostTitle() ;
                        String textStr = item.getPostInfo() ;
                        String tagStr = item.getPostTag();
                        String usernameStr = item.getUserId();
                        Integer grade = userGrade;

                        // TODO : use item data.
                        Bundle bundle = new Bundle();
                        bundle.putInt("position",pos);
                        bundle.putString("title_text",titleStr);
                        bundle.putString("main_text",textStr);
                        bundle.putString("tag_text",tagStr);
                        bundle.putString("user_name",usernameStr);
                        bundle.putInt("user_grade",grade);

                        CommentSimple comment = new CommentSimple();
                        comment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
                        /*FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                        Fragment frag = activity.getSupportFragmentManager().findFragmentById(R.id.main_frame);
                        transaction.add(R.id.main_frame,comment);
                        transaction.show(comment);
                        transaction.hide(frag);
                        transaction.commit();*/
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame,comment)
                                //.addToBackStack(null)
                                .commit();

                    }
                }
            });
        }
        public void setItem(PostItem item){
            username.setText(item.getUserId());
            titleText.setText(item.getPostTitle());
            tagText.setText(item.getPostTag());
            like_count.setText(String.valueOf(item.getLike_count()));
            bookmark_count.setText(String.valueOf(item.getBookmark_count()));
            Glide
                    .with(context)
                    .load(item.getUserIcon())
                    .apply(new RequestOptions().override(50,50))
                    .into(userImage);
        }
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.post_feedview_item, parent, false);
            PostAdapter.ViewHolder vh = new PostAdapter.ViewHolder(view);
            return vh;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem item = postItems.get(position);
        holder.setItem(item);
        holder.bpa = item.getViewPagerAdapter();

        holder.vp.setAdapter(holder.bpa);
        holder.vp.setId(position+1);
        holder.vp.setOffscreenPageLimit(0);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position ;
    }
    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void setItems(ArrayList<PostItem> items){
        postItems = items;
    }

    public PostItem getItem(int position){
        return postItems.get(position);
    }

    public void setItem(int position, PostItem item){
        postItems.set(position, item);
    }

    public FragmentManager getFm(){
        return fm;
    }

    public int getPosition(){
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
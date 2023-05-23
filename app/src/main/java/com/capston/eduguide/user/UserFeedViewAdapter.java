package com.capston.eduguide.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.R;
import com.capston.eduguide.login.User;
import com.capston.eduguide.post.CommentSimple;
import com.capston.eduguide.post.FeedViewItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFeedViewAdapter extends RecyclerView.Adapter<UserFeedViewAdapter.ViewHolder> {

    private static ArrayList<FeedViewItem> items;
    private final Context mContext;
    private static String userEmail; // userEmail 추가

    // 생성자
    public UserFeedViewAdapter(Context context, ArrayList<FeedViewItem> feedList, String userEmail) {
        mContext = context;
        items = feedList;
        this.userEmail = userEmail;
    }

    // 뷰 홀더 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // 뷰 홀더 바인딩
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FeedViewItem currentFeed = items.get(position);
        holder.mFeedTitle.setText(currentFeed.getTitle());

        // userEmail을 사용하여 사용자 검색 및 게시물 필터링
        String userEmail = currentFeed.getUserEmail();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null) {
                            String userName = user.getName();
                            if (userName.equals(currentFeed.getUserName())) {
                                // 사용자 이름과 게시물의 작성자 이름이 일치하면 아이템 추가
                                holder.mFeedTitle.setText(currentFeed.getTitle() + " by " + userName);
                                items.add(currentFeed);
                                notifyDataSetChanged();
                                break; // 추가된 아이템이 있으면 반복문 종료
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리 중단 또는 에러 처리
            }
        });
    }


    // 뷰 홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFeedTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mFeedTitle = itemView.findViewById(R.id.user_titleView);

            mFeedTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //현수꺼 피드 레이아웃으로 연결 되게 해서 그 레이아웃으로 현재 사용자의 게시글 불러오기
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        FeedViewItem item = items.get(pos);
                        String titleStr = item.getTitle() ;
                        String textStr = item.getText() ;
                        String tagStr = item.getTag();
                        String usernameStr = item.getUserName(); // 이 부분 수정해야 할 것임,
                        Integer grade = 0;

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
    }

    // 어댑터가 관리하는 아이템의 개수 반환
    @Override
    public int getItemCount() {
        return items.size();
    }
}
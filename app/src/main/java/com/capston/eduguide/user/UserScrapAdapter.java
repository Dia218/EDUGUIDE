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

public class UserScrapAdapter extends RecyclerView.Adapter<UserScrapAdapter.ViewHolder> {

    private static ArrayList<FeedViewItem> items;
    private final Context mContext;
    private String userEmail;

    public UserScrapAdapter(Context context, ArrayList<FeedViewItem> feedList, String userEmail) {
        mContext = context;
        items = feedList;
        this.userEmail = userEmail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_scrap_grid, parent, false);
        return new ViewHolder(view);
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

        String lastWord = userEmail.substring(userEmail.lastIndexOf('.') + 1);
        String bookmarkKey = "Bookmark_" + lastWord;
        DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("Bookmark");
        bookmarkRef.child(bookmarkKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild(currentFeed.getFeedId())) {
                    holder.mFeedTitle.setText(currentFeed.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리 중단 또는 에러 처리
            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFeedTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mFeedTitle = itemView.findViewById(R.id.user_scrap_titleView);

            mFeedTitle.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    FeedViewItem item = items.get(pos);
                    String titleStr = item.getTitle();
                    String textStr = item.getText();
                    String tagStr = item.getTag();
                    String usernameStr = item.getUserName();
                    Integer grade = 0;

                    Bundle bundle = new Bundle();
                    bundle.putInt("position", pos);
                    bundle.putString("title_text", titleStr);
                    bundle.putString("main_text", textStr);
                    bundle.putString("tag_text", tagStr);
                    bundle.putString("user_name", usernameStr);
                    bundle.putInt("user_grade", grade);

                    CommentSimple comment = new CommentSimple();
                    comment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frame, comment)
                            .commit();
                }
            });
        }
    }
}

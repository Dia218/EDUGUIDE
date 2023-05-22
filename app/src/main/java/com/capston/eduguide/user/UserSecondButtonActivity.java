package com.capston.eduguide.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.R;
import com.capston.eduguide.post.FeedViewItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserSecondButtonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserFeedViewAdapter adapter;
    private ArrayList<FeedViewItem> feedList;

    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag5_user);

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.my_posts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // 어댑터 초기화
        feedList = new ArrayList<>(); // 적절한 FeedViewItem 리스트를 설정해야 합니다.
        adapter = new UserFeedViewAdapter(this, feedList);
        recyclerView.setAdapter(adapter);

        // 두 번째 버튼 클릭 시 RecyclerView 갱신
        Button secondButton = findViewById(R.id.second_button);
        secondButton.setOnClickListener(v -> {
            Log.d("UserSeconButtonActivity", "Second button clicked");
            // Firebase 인증에서 현재 사용자 정보 가져오기
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                currentUserEmail = user.getEmail();

                // Firebase Realtime Database에서 현재 사용자의 북마크된 게시물 가져오기
                DatabaseReference bookmarkRef = FirebaseDatabase.getInstance().getReference("bookmark")
                        .child(currentUserEmail);

                bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        feedList.clear(); // 기존 데이터를 지우고 새로운 데이터로 갱신

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String postId = snapshot.child("postId").getValue(String.class);
                            if (postId != null) {
                                // postId를 사용하여 해당 게시물 가져오기
                                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("posts")
                                        .child(postId);

                                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        // 게시물의 데이터를 가져와서 FeedViewItem으로 변환하여 리스트에 추가
                                        FeedViewItem feedViewItem = dataSnapshot.getValue(FeedViewItem.class);
                                        if (feedViewItem != null) {
                                            feedList.add(feedViewItem);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // 가져오기 실패 시 처리할 내용
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 가져오기 실패 시 처리할 내용
                    }
                });
            }
        });
    }
}
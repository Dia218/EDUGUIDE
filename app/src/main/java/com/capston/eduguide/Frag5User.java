package com.capston.eduguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.login.User;
import com.capston.eduguide.post.FeedViewItem;
import com.capston.eduguide.user.SettingsActivity;
import com.capston.eduguide.user.UserFeedViewAdapter;
import com.capston.eduguide.user.UserScrapAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Frag5User extends Fragment {

    private TextView profileNameTextView;
    private TextView selfIntroTextView;

    ArrayList<FeedViewItem> items;
    UserFeedViewAdapter adapter;

    private String userEmail;
    private String feedId;
    private Integer userGrade;
    private Integer feedUserGrade;
    private DatabaseReference userDatabaseReference;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag5_user, container, false);

        // userEmail 값을 Bundle에서 받아오기
        if (getArguments() != null) {
            userEmail = getArguments().getString("userEmail");
        }

        ImageButton settingsButton = view.findViewById(R.id.settings_icon);

        settingsButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra("userEmail", userEmail); // userEmail 전달
                startActivity(intent);
            }
        });


        // 저장된 닉네임과 자기소개 가져와서 TextView에 반영하기
        profileNameTextView = view.findViewById(R.id.profile_name);
        selfIntroTextView = view.findViewById(R.id.self_intro);

        updateProfileInfo();

        RecyclerView rcView = view.findViewById(R.id.my_posts);
        items = new ArrayList<>();
        adapter = new UserFeedViewAdapter(getContext(), items, userEmail, userGrade);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcView.setLayoutManager(gridLayoutManager);
        rcView.setAdapter(adapter);

        userDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userId = "";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            userId = user.getId();
                        }
                    }
                    loadUserPosts(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리 중단 또는 에러 처리
            }
        });

        return view;
    }

    private void loadUserPosts(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("post");

        databaseReference.orderByChild("userName").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedViewItem item = dataSnapshot.getValue(FeedViewItem.class);
                    items.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리 중단 또는 에러 처리
            }
        });

    }


    //피드 아이템의 유저 이름으로 등급 검색, 검색된 등급을 피드의 등급에 저장하고 그걸로 뱃지 표시
    public void setUserIconForGrade(FeedViewItem item){

        String feedUserName = item.getUserName();
        feedUserGrade = 5;

        userDatabaseReference = database.getReference("users");
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HashMap<String, String> value = (HashMap<String, String>)dataSnapshot.getValue();
                    if(String.valueOf(feedUserName).equals(value.get("name"))){
                        feedUserGrade = Integer.parseInt(value.get("grade"));
                        item.setGrade(feedUserGrade);
                        break;
                    }
                    else if(String.valueOf(feedUserName).equals("null")){
                        feedUserGrade = 5;
                        item.setGrade(feedUserGrade);
                        break;
                    }
                }
                if(feedUserGrade==0){
                    item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.seed, null));
                }
                else if (feedUserGrade==1) {
                    item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.sprout, null));
                }
                else if (feedUserGrade==2) {
                    item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.seedling, null));
                }
                else if (feedUserGrade==3) {
                    item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.tree, null));
                }
                else if (feedUserGrade==5)
                    item.setUserIcon(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.grade1, null));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProfileInfo() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = databaseReference.child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            String nickname = user.getNickname();
                            String intro = user.getIntro();

                            profileNameTextView.setText(nickname);
                            selfIntroTextView.setText(intro);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // 처리 중단 또는 에러 처리
                    Toast.makeText(getContext(), "데이터베이스 오류: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
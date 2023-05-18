package com.capston.eduguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.post.FeedViewItem;
import com.capston.eduguide.user.SettingsActivity;
import com.capston.eduguide.user.UserFeedViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag5User extends Fragment {

    private TextView profileNameTextView;
    private TextView selfIntroTextView;
    ArrayList<FeedViewItem> items;
    UserFeedViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag5_user, container, false);

        ImageButton settingsButton = (ImageButton) view.findViewById(R.id.settings_icon);

        settingsButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 저장된 닉네임과 자기소개 가져와서 TextView에 반영하기
        profileNameTextView = view.findViewById(R.id.profile_name);
        selfIntroTextView = view.findViewById(R.id.self_intro);

        updateProfileInfo();

        RecyclerView rcView = (RecyclerView) view.findViewById(R.id.my_posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcView.setLayoutManager(gridLayoutManager);

        items = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("post");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot Snapshot : snapshot.getChildren()){
                    FeedViewItem item = Snapshot.getValue(FeedViewItem.class);
                    items.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new UserFeedViewAdapter(getContext(),items);
        rcView.setAdapter(adapter);

        return view;
    }

    public void updateProfileInfo() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
            String savedName = sharedPreferences.getString("name", "");
            String savedIntro = sharedPreferences.getString("intro", "");

            profileNameTextView.setText(savedName);
            selfIntroTextView.setText(savedIntro);
        }
    }

}


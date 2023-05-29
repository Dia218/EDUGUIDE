package com.capston.eduguide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Frag4Notice extends Fragment {

    public static Frag4Notice frag4Notice = new Frag4Notice();

    private View view;

    private TextView noticeText;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;


    String userEmail;
    String userName;
    Integer userGrade;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag4_notice, container, false);
        noticeText = view.findViewById(R.id.notice_text);

        firebaseDatabase = FirebaseDatabase.getInstance();

        Bundle bundle = getArguments();
        if (userEmail == null){
            if(bundle.getString("userEmail")!= null) {
                userEmail = bundle.getString("userEmail");
                callUserName();
            }
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String feedUserId = dataSnapshot.getKey();
                    if (userName.equals(feedUserId)) {
                        String title = dataSnapshot.getValue(String.class);
                        showNewCommentNotification(title);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    public void showNewCommentNotification(String title){
        String notificationText = "'" + title + "' 게시글에 새로운 댓글이 달렸습니다.";
        noticeText.setText(notificationText);
    }
    public void callUserName(){

        if(userEmail != null){
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        HashMap<String, String> value = (HashMap<String, String>)dataSnapshot.getValue();
                        if(userEmail.equals(value.get("email"))){
                            userName = value.get("name");
                            userGrade = Integer.parseInt(value.get("grade"));

                        }
                    }
                    if(userName == null){
                        userName = "";

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            userName = "";
        }
    }
}
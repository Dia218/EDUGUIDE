package com.capston.eduguide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.guideTool.GuideTool;
import com.capston.eduguide.post.PostItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Frag3Posting extends Fragment {

    private View view;
    private ViewPager vp;
    private Integer prepId;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference postDatabaseReference = firebaseDatabase.getReference("post");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag3_posting, container, false);

        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        AppCompatEditText postTitle = view.findViewById(R.id.postTitle); //제목
        AppCompatEditText postInfo = view.findViewById(R.id.postInfo); //내용
        AppCompatEditText postTag = view.findViewById(R.id.postTag); //태그

        postDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("post").getKey() != null)
                        count += 1;
                }
                prepId = count;
                Log.d("pId_test","preId:"+prepId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //등록 버튼 이벤트리스너
        view.findViewById(R.id.postingSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //유저 아이디 로직 추가 필요
                Integer pWriterId = 0;
                
                String pTitle = String.valueOf(postTitle.getText()); //제목 받아오기
                String pInfo = String.valueOf(postInfo.getText()); //내용 받아오기
                String pTag = String.valueOf(postTag.getText()); //태그 받아오기

                //게시글DB 등록
                PostItem item = new PostItem();

                item.setPostTitle(pTitle);
                item.setPostInfo(pInfo);
                item.setPostTag(pTag);
                item.setUserId("test_id1");
                item.setGrade(0);
                item.setBookmark_count(0);
                item.setLike_count(0);
                String fId = prepId.toString();
                //item.setFeedId(prepId+1);

                databaseReference.child("post").child(fId).setValue(item);

            }
        });

        return view;
    }

    private class BannerPagerAdapter extends FragmentPagerAdapter {

        public BannerPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return GuideTool.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

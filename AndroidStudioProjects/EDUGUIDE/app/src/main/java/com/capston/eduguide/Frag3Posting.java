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

import com.capston.eduguide.guideTool.GuideFragment;
import com.capston.eduguide.post.FeedViewItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Frag3Posting extends Fragment {

    private View view;
    private ViewPager vp;
    private String prepId;
    private String userId;

    private
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference userDatabaseReference = firebaseDatabase.getReference("users");
    DatabaseReference postDatabaseReference = firebaseDatabase.getReference("post");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag3_posting, container, false);

        vp = (ViewPager) view.findViewById(R.id.vp);
        FeedViewItem.BannerPagerAdapter bannerPagerAdapter = new FeedViewItem.BannerPagerAdapter(getChildFragmentManager());
        vp.setAdapter(bannerPagerAdapter);
        vp.setCurrentItem(0);

        Bundle bundle = getArguments();

        AppCompatEditText postTitle = view.findViewById(R.id.postTitle); //제목
        AppCompatEditText postInfo = view.findViewById(R.id.postInfo); //내용
        AppCompatEditText postTag = view.findViewById(R.id.postTag); //태그

        postDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey() != null) {
                        prepId = snapshot.getKey();
                    }
                }
                Log.d("pId_test2","preId:"+prepId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> user = (HashMap<String, Object>)userSnapshot.getValue();
                    if(bundle.getString("userEmail")!=null) {
                        if (bundle.getString("userEmail").equals((String) user.get("email"))) {
                            userId = (String)user.get("id");
                        }
                    }
                }
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

                FeedViewItem item = new FeedViewItem();

                item.setTitle(pTitle);
                item.setText(pInfo);
                item.setTag(pTag);
                if(userId!=null)
                    item.setUserId(userId);
                item.setGrade(0);
                item.setBookmark_count(0);
                item.setLike_count(0);
                String fId = fId(prepId);
                item.setFeedId(fId);

                databaseReference.child("post").child(fId).setValue(item);

                GuideFragment guideAdapter = (GuideFragment) bannerPagerAdapter.getItem(vp.getCurrentItem());
                guideAdapter.regGuideContent(fId);

                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) { activity.replaceFragment(new Frag1Feed()); } // 등록 후 메인 피드로 전환
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
            return GuideFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
    private String fId(String prepId){
        Integer fIdNum = Integer.parseInt(prepId)+1;
        return String.valueOf(fIdNum);
    }
}

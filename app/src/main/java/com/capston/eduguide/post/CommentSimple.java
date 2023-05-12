package com.capston.eduguide.post;

import static com.capston.eduguide.Frag4Notice.frag4Notice;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.Frag1Feed;
import com.capston.eduguide.MainActivity;
import com.capston.eduguide.R;
import com.capston.eduguide.guideTool.GuideTool;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentSimple extends Fragment {

    /*public static Comment newInstance(){
        return new Comment();
    }*/
    private TextView main;
    private TextView tag;
    private TextView username;
    private ViewPager vp;
    private EditText comm;
    private Integer gradeInt;
    private ImageView userImage;
    private ImageView feedUserImage;
    private String fId;
    FeedViewItem item;
    CommentSimpleAdapter adapter;
    ArrayList<CommentItem> comments = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference DatabaseReference = firebaseDatabase.getReference("comment");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 뒤로가기 버튼을 누르면 메인피드로 화면 전환
                ((MainActivity)getActivity()).replaceFragment(new Frag1Feed());

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag1_detail, container, false);

        main = view.findViewById(R.id.commentMain);
        tag = view.findViewById(R.id.tag);
        username = view.findViewById(R.id.userName);
        comm = view.findViewById(R.id.commentDetail);
        feedUserImage = view.findViewById(R.id.feedUserImage);
        userImage = view.findViewById(R.id.userImage);
        //Button back = (Button)view.findViewById(R.id.back);
        Button input = (Button) view.findViewById(R.id.commentInput);
        item = new FeedViewItem();

        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        Bundle bundle = getArguments();
        if (getArguments() != null)
        {
            String mainStr = bundle.getString("main_text");
            String tagStr = bundle.getString("tag_text");
            String userName = bundle.getString("feedUser_name");
            main.setText(mainStr);
            tag.setText(tagStr);
            username.setText(userName);
            gradeInt =  bundle.getInt("user_grade");
            feedUserImage.setImageResource(grade(gradeInt));
            Integer pos = bundle.getInt("position");
            fId = pos.toString();
            //사용자의 뱃지 이미지. 일단은 게시글 유저의 등급 받아옴 - 추후에 db에서 사용자의 등급 받아와야함
            userImage.setImageResource(grade(gradeInt));
        }

        ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CommentItem comm = snapshot.getValue(CommentItem.class);
                    comments.add(comm);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseReference.child(fId).addValueEventListener(mListener);

        // 댓글 리사이클러뷰 참조
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 댓글 리사이클러뷰에 Adapter 객체 지정
        adapter = new CommentSimpleAdapter(getContext(),comments,getChildFragmentManager());
        recyclerView.setAdapter(adapter);

        input.setOnClickListener(new View.OnClickListener() {                      //댓글 입력시 이벤트
            @Override
            public void onClick(View v) {
                String comment = comm.getText().toString();
                //유저 db가 생기면 db에서 데이터 받아오기. 현재는 게시글의 데이터 받아옴
                String userId = bundle.getString("userId");
                //여기서 유저의 등급 받아서 코멘트 속성으로 부여할지 고민중
                //현재는 어댑터에서 출력하기 전에 유저 이름으로 등급 검색함
                comm.setText("");
                adapter.addComment(comments,userId,comment);
                adapter.notifyItemInserted(comments.size());
                recyclerView.setAdapter(adapter);

                //파이어베이스에 데이터 입력
                DatabaseReference.child(fId).setValue(comments);

                //알림 호출 //게시글 제목 받아와야 함
                //frag4Notice.showNotification(0, "게시글 제목", userId);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(comments.size()-1);
                    }
                }, 200);

                //recyclerView.scrollToPosition(comments.size()-1);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private int grade(Integer gradeInt) {
        if(gradeInt == 0)
            return R.drawable.seed;
        else if(gradeInt == 1)
            return R.drawable.sprout;
        else if(gradeInt == 2)
            return R.drawable.seedling;
        else if(gradeInt == 3)
            return R.drawable.tree;
        else
            return R.drawable.grade1;
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

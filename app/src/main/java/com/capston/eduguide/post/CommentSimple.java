package com.capston.eduguide.post;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.capston.eduguide.guideTool.GuideFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String userName;
    private Integer userGrade;
    private ImageView feedUserImage;
    private String feedUserName;
    private String fId;
    FeedViewItem item;
    CommentSimpleAdapter adapter;
    FeedViewItem.BannerPagerAdapter bpa;
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
                Bundle bundle = new Bundle();
                bundle.putString("userName",userName);
                bundle.putInt("userGrade",userGrade);
                Frag1Feed feed = new Frag1Feed();
                feed.setArguments(bundle);
                ((MainActivity) requireActivity()).replaceFragment(feed);
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
        Button delete = (Button)view.findViewById(R.id.deleteButton);
        Button input = (Button) view.findViewById(R.id.commentInput);
        //item = new FeedViewItem();

        Bundle bundle = getArguments();
        if (getArguments() != null)
        {
            userName = bundle.getString("userName");
            String mainStr = bundle.getString("main_text");
            String tagStr = bundle.getString("tag_text");
            String userName = bundle.getString("feedUser_name");
            feedUserName = bundle.getString("feedUser_name");
            main.setText(mainStr);
            tag.setText(tagStr);
            username.setText(userName);
            gradeInt =  bundle.getInt("feedUser_grade");
            feedUserImage.setImageResource(grade(gradeInt));
            Integer pos = bundle.getInt("position");
            fId = bundle.getString("feedId");
            userGrade = bundle.getInt("userGrade");
            //사용자의 뱃지 이미지. 일단은 게시글 유저의 등급 받아옴 - 추후에 db에서 사용자의 등급 받아와야함
            userImage.setImageResource(grade(userGrade));
        }

        vp = (ViewPager) view.findViewById(R.id.vp);
        bpa = new FeedViewItem.BannerPagerAdapter(getChildFragmentManager());
        bpa.getGuide(fId);
        vp.setAdapter(bpa);
        vp.setCurrentItem(0);

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
        adapter.setUserName(userName);
        adapter.setFeedId(fId);
        recyclerView.setAdapter(adapter);

        if(userName!=null){
            if(userName.equals(feedUserName)){
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        database.getReference().child("post").child(fId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Bundle bundle = new Bundle();
                                bundle.putString("userName",userName);
                                Frag1Feed feed = new Frag1Feed();
                                feed.setArguments(bundle);
                                ((MainActivity) requireActivity()).replaceFragment(feed);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            else{
                delete.setVisibility(View.GONE);
            }
        }
        else{
            delete.setVisibility(View.GONE);
            input.setVisibility(View.GONE);
            comm.setClickable(false);
        }

        input.setOnClickListener(new View.OnClickListener() {                      //댓글 입력시 이벤트
            @Override
            public void onClick(View v) {
                String comment = comm.getText().toString();
                String userName = bundle.getString("userName");
                //여기서 유저의 등급 받아서 코멘트 속성으로 부여할지 고민중
                //현재는 어댑터에서 출력하기 전에 유저 이름으로 등급 검색함
                comm.setText("");
                adapter.addComment(comments,userName,comment);
                adapter.notifyItemInserted(comments.size());
                recyclerView.setAdapter(adapter);

                //파이어베이스에 데이터 입력
                DatabaseReference.child(fId).setValue(comments);
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
}
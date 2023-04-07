package com.capston.eduguide;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.bottomnavi.R;

import java.util.ArrayList;

public class CommentSimple extends Fragment {

    /*public static Comment newInstance(){
        return new Comment();
    }*/
    private View view;
    private TextView main;
    private TextView tag;
    private TextView username;
    private ViewPager vp;
    private EditText comm;
    private Integer gradeInt;
    private ImageView userImage;
    private ImageView feedUserImage;
    ArrayList<CommentItem> comments = new ArrayList<>();

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
        Button back = (Button)view.findViewById(R.id.back);
        Button input = (Button) view.findViewById(R.id.commentInput);
        Bundle bundle = getArguments();
        if (getArguments() != null)
        {
            String mainStr = bundle.getString("main_text"); // 피드에서 받아온 값 넣기
            main.setText(mainStr);
            String tagStr = bundle.getString("tag_text");
            tag.setText(tagStr);
            String usernameStr = bundle.getString("user_name");
            username.setText(usernameStr);
            gradeInt =  bundle.getInt("user_grade");
            feedUserImage.setImageResource(grade(gradeInt));
            //사용자의 뱃지 이미지. 일단은 게시글 유저의 등급 받아옴 - 추후에 db에서 사용자의 등급 받아와야함
            userImage.setImageResource(grade(gradeInt));
        }
        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        back.setOnClickListener(new View.OnClickListener() {              //피드 메인으로 돌아가는 버튼 이벤트
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).replaceFragment(Frag1Feed.newInstance());
                Frag1Feed feed = new Frag1Feed();
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,feed).commit();
            }
        });

        // 댓글 리사이클러뷰 참조
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 댓글 리사이클러뷰에 Adapter 객체 지정
        CommentSimpleAdapter adapter = new CommentSimpleAdapter(getContext(),comments,getChildFragmentManager());
        recyclerView.setAdapter(adapter);

        //댓글아이템 추가
        /*adapter.addComment(comments,ResourcesCompat.getDrawable(requireActivity().getResources(),grade(gradeInt),null),
                "댓글단 유저 아이디1","임시 댓글 내용1");*/

        input.setOnClickListener(new View.OnClickListener() {                      //댓글 입력시 이벤트
            @Override
            public void onClick(View v) {
                String comment = comm.getText().toString();
                //유저 db가 생기면 db에서 데이터 받아오기. 현재는 게시글의 데이터 받아옴
                String userId = bundle.getString("user_name");
                Integer userGrade = bundle.getInt("user_grade");
                comm.setText("");
                adapter.addComment(comments,ResourcesCompat.getDrawable(requireActivity().getResources(),grade(userGrade),null),userId,comment);
                //comments.add(new CommentItem(ResourcesCompat.getDrawable(requireActivity().getResources(),grade(gradeInt),null), comment,userId));
                adapter.notifyItemInserted(comments.size());
                recyclerView.setAdapter(adapter);

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
        if(gradeInt < 10)
            return R.drawable.grade1;
        else if(gradeInt < 20)
            return R.drawable.grade1;
        else if(gradeInt < 30)
            return R.drawable.grade1;
        else if(gradeInt < 40)
            return R.drawable.grade1;
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
            return Guide.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

package com.capston.eduguide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capston.eduguide.R;

import java.util.ArrayList;

public class Comment extends Fragment {

    //
    public static Comment newInstance(){
        return new Comment();
    }
    private View view;
    private TextView main;
    private TextView tag;
    private TextView username;
    private String mainStr;
    private String tagStr;
    private String usernameStr;
    private ViewPager vp;
    ArrayList<CommentItem> comments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.frag1_comment, container, false);

        Bundle bundle = getArguments();
        main = view.findViewById(R.id.commentMain);
        tag = view.findViewById(R.id.tag);
        username = view.findViewById(R.id.userName);
        Button back = (Button)view.findViewById(R.id.back);
        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setAdapter(new BannerPagerAdapter(getChildFragmentManager()));
        vp.setCurrentItem(0);

        if (getArguments() != null)
        {
            mainStr = bundle.getString("main_text"); // 프래그먼트1에서 받아온 값 넣기
            main.setText(mainStr);
            tagStr = bundle.getString("tag_text");
            tag.setText(tagStr);
            usernameStr = bundle.getString("user_name");
            username.setText(usernameStr);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Frag1Feed.newInstance());
            }
        });

        ListView commentView;
        CommentAdapter adapter;

        adapter = new CommentAdapter(getContext());

        commentView = (ListView) view.findViewById(R.id.commentView);
        commentView.setAdapter(adapter);

        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디1","임시 댓글 내용1");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디2","임시 댓글 내용2");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");
        adapter.addComment(ResourcesCompat.getDrawable(requireActivity().getResources(),R.drawable.person,null),"댓글단 유저 아이디3","임시 댓글 내용3\n\n\n");

        commentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommentItem comment = (CommentItem) parent.getItemAtPosition(position);
                Drawable userImage = comment.getUserIcon();
                String username = comment.getUsername();
                String commentStr = comment.getComment();


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
            return Guide.newInstance(position);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}

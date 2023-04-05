package com.capston.eduguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.capston.eduguide.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentItem> commentItemList = new ArrayList<>();

    class CommentHolder {
        public TextView username;
        public TextView commentText;
        public ImageView userImage;
    }
    //CommentAdapter의 생성자
    public CommentAdapter(Context context) {
        this.context = context;
    }
    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return commentItemList.size();
    }
    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return commentItemList.get(position);
    }
    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentHolder commentHolder;

        // "comment_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.comment_item, parent, false);
        }

        commentHolder = new CommentHolder();
        commentHolder.username = (TextView) convertView.findViewById(R.id.commentUserName);
        commentHolder.commentText = (TextView) convertView.findViewById(R.id.comment);
        commentHolder.userImage = (ImageView) convertView.findViewById(R.id.commentUserImage);

        // Data Set(commentItemList)에서 position에 위치한 데이터 참조 획득
        CommentItem item = commentItemList.get(position);
        commentHolder.username.setText(item.getUsername());
        commentHolder.commentText.setText(item.getComment());
        Glide
                .with(context)
                .load(item.getUserIcon())
                .apply(new RequestOptions().override(50,50))
                .into(commentHolder.userImage);

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addComment(Drawable userImage, String userName, String comment){
        CommentItem item = new CommentItem();

        item.setUserIcon(userImage);
        item.setUsername(userName);
        item.setComment(comment);

        commentItemList.add(item);
    }
}

package com.capston.eduguide.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.R;

import java.util.ArrayList;

public class CommentSimpleAdapter extends RecyclerView.Adapter<CommentSimpleAdapter.ViewHolder> {

    private Context context;
    public ArrayList<CommentItem> commentItemList;
    private FragmentManager fm;

    public interface OnItemClickEventListener { void onItemClick(int a_position);}

    private OnItemClickEventListener mItemClickListener = new OnItemClickEventListener() {
        @Override
        public void onItemClick(int a_position) {
            notifyItemChanged(mCheckedPosition, null);
            mCheckedPosition = a_position;
            notifyItemChanged(a_position, null);
        }
    };

    private int mCheckedPosition = -1;

    //CommentAdapter의 생성자
    public CommentSimpleAdapter(Context context, ArrayList<CommentItem> commentItemList, FragmentManager fm) {
        this.context = context;
        this.commentItemList = commentItemList;
        this.fm = fm;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView commentText;
        public ImageView userImage;
        //public ArrayList<ReplyItem_unfinished> replyItems;
        //private RecyclerView rvReplyItem;

        //클릭리스너는 뷰홀더에서 작성
        public ViewHolder(@NonNull View itemView, final OnItemClickEventListener a_itemClickListener) {
            super(itemView);
            // 뷰 객체에 대한 참조(부모).
            username = itemView.findViewById(R.id.commentUserName);
            commentText = itemView.findViewById(R.id.comment);
            userImage = itemView.findViewById(R.id.commentUserImage);
            // 뷰 객체에 대한 참조(자식아이템).
            //rvReplyItem = itemView.findViewById(R.id.replyList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View a_view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        a_itemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.post_comment_item, parent, false);
        CommentSimpleAdapter.ViewHolder cvh = new CommentSimpleAdapter.ViewHolder(view,mItemClickListener);

        return cvh;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CommentItem item = commentItemList.get(position);

        final int color;
        if (holder.getAdapterPosition() == mCheckedPosition) {
            color = ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_200);
        } else {
            color = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent);
        }
        holder.itemView.setBackgroundColor(color);

        holder.username.setText(item.getUsername());
        holder.commentText.setText(item.getComment());
        holder.userImage.setImageResource(R.drawable.grade1);   //db에서 gradeInt를 받아와서 각 사용자의 뱃지 이미지 받아와야함

    }



    //지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public int getItemCount() { return commentItemList.size(); }

    // 아이템 데이터 추가를 위한 함수
    public void addComment(ArrayList<CommentItem> commentItemList, Drawable userImage, String userName, String comment){
        CommentItem item = new CommentItem(userImage,comment,userName);

        item.setUserIcon(userImage);
        item.setUsername(userName);
        item.setComment(comment);

        commentItemList.add(item);
    }

    public CommentItem getSelected(){
        if (mCheckedPosition > -1) {
            return commentItemList.get(mCheckedPosition);
        }
        return null;
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public void clearSelected() {
        mCheckedPosition = -1;
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
}

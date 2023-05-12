package com.capston.eduguide.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<SearchItem> searchItems;
    private Context context;

    private static OnItemClickListener onItemClickListener;


    public SearchAdapter(Context context,OnItemClickListener onItemClickListener){
        this.context=context;
        searchItems=new ArrayList<>();
        this.onItemClickListener=onItemClickListener;
    }
    public void setSearchItems(List<SearchItem> searchItems){
        this.searchItems.clear();
        this.searchItems.addAll(searchItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view=LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new SearchViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder,int position){
        SearchItem searchItem =searchItems.get(position) ;
        Log.d("SearchAdapter", "onBindViewHolder: " + searchItem.getTitle() + ", " + searchItem.getTag());
        holder.bind(searchItem);
        Log.d("SearchAdapter", "onBindViewHolder position: " + position);
        holder.itemView.setOnClickListener(v-> onItemClickListener.onItemClick(searchItem));
    }
    public int getItemCount(){
        return searchItems.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView tagTextView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            tagTextView = itemView.findViewById(R.id.text_view_tag);
        }

        public void bind(SearchItem searchItem) {
            titleTextView.setText(searchItem.getTitle());
            tagTextView.setText("#"+searchItem.getTag());
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(searchItem));

        }
    }
    public interface OnItemClickListener{
        void onItemClick(SearchItem searchItem);
    }
}

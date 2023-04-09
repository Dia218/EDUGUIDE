package com.capston.eduguide.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<SearchItem> searchlist;

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1;
        public TextView textView2;

        public SearchViewHolder(@NonNull View itemView){
            super(itemView);
            textView1 =itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
        }
    }
    public SearchAdapter(ArrayList<SearchItem> searchlist){
        this.searchlist=searchlist;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        SearchViewHolder evh = new SearchViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position){
        SearchItem currentitem = searchlist.get(position);
        holder.textView1.setText(currentitem.getTitle());
        holder.textView2.setText(currentitem.getTag());
    }
    @Override
    public int getItemCount(){
        return searchlist.size();
    }
}

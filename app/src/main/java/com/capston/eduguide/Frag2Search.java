package com.capston.eduguide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capston.eduguide.db.DatabaseHelper;
import com.capston.eduguide.post.CommentSimple;
import com.capston.eduguide.search.SearchAdapter;
import com.capston.eduguide.search.SearchItem;

import java.util.ArrayList;

public class Frag2Search extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;


    public Frag2Search() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2_search, container, false);

        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recycle_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        private void searchDatabase (String searchText){
            Query query = databaseReference.child("tags").orderByChild("tag").startAt(searchText).endAt(searchText + "\uf8ff");

            FirebaseRecyclerOptions<Model> options =
                    new FirebaseRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
            FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                            holder.setDetails(getActivity().getApplicationContext(), model.getTitle(), model.getTag());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userId", model.getUserId());
                                    bundle.putString("description", model.getDescription());
                                    bundle.putString("tag", model.getTag());

                                    Fragment fragment = new OtherFragment();
                                    fragment.setArguments(bundle);

                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_container, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();

                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout, parent, false);
                            ViewHolder viewHolder = new ViewHolder(view);
                            return viewHolder;
                        }
                    };
            firebaseRecyclerAdapter.startListening();
            recyclerView.setAdapter(firebaseRecyclerAdapter);
        }

    }
}

package com.narara.review_study.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.narara.review_study.MainViewModel;
import com.narara.review_study.R;
import com.narara.review_study.databinding.ItemAlbumBinding;
import com.narara.review_study.models.Album;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumListFragment extends Fragment {


    public AlbumListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int userId = AlbumListFragmentArgs.fromBundle(getArguments()).getUserId();

        AlbumListViewModel albumListViewModel = ViewModelProviders.of(this).get(AlbumListViewModel.class);
        // MainViewModel mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);

        AlbumAdapter adapter = new AlbumAdapter(new AlbumAdapter.OnAlbumClickListener() {
            @Override
            public void onAlbumClicked(Album model) {
                AlbumListFragmentDirections.ActionAlbumListFragmentToPhotoListFragment action =
                        AlbumListFragmentDirections.actionAlbumListFragmentToPhotoListFragment(model.getId());

                Navigation.findNavController(view).navigate(action);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        albumListViewModel.albums.observe(this, albums -> adapter.setItems(albums));

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        albumListViewModel.isProgressing.observe(this, isProgressing -> {
            if (isProgressing) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        albumListViewModel.fetchAlbums(userId);
    }

    private static class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
        interface OnAlbumClickListener {
            void onAlbumClicked(Album model);
        }

        private OnAlbumClickListener mListener;

        private List<Album> mItems = new ArrayList<>();

        public AlbumAdapter() {
        }

        public AlbumAdapter(OnAlbumClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Album> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_album, parent, false);
            final AlbumHolder viewHolder = new AlbumHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Album item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.onAlbumClicked(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Album item = mItems.get(position);
            // TODO : 데이터를 뷰홀더에 표시하시오
            holder.binding.setAlbum(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class AlbumHolder extends RecyclerView.ViewHolder {
            // TODO : 뷰홀더 완성하시오
            ItemAlbumBinding binding;

            public AlbumHolder(@NonNull View itemView) {
                super(itemView);
                // TODO : 뷰홀더 완성하시오
                binding = ItemAlbumBinding.bind(itemView);
            }
        }
    }

}

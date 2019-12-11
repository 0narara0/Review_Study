package com.narara.review_study.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.review_study.MainViewModel;
import com.narara.review_study.R;
import com.narara.review_study.databinding.FragmentPhotoListBinding;
import com.narara.review_study.databinding.ItemPhotoBinding;
import com.narara.review_study.models.Photo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment {


    public PhotoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        FragmentPhotoListBinding binding = FragmentPhotoListBinding.bind(view);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        PhotoAdapter adapter = new PhotoAdapter(model -> {
            PhotoListFragmentDirections.ActionPhotoListFragmentToPhotoDetailFragment action =
                    PhotoListFragmentDirections.actionPhotoListFragmentToPhotoDetailFragment(model.getUrl());
            Navigation.findNavController(view).navigate(action);
        });

        binding.recyclerView.setAdapter(adapter);
        
        int albumId = PhotoListFragmentArgs.fromBundle(getArguments()).getAlbumId();
        
        viewModel.fetchPhotos(albumId);

    }

    public static class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
        interface OnPhotoClickListener {
            void onPhotoClicked(Photo model);
        }

        private OnPhotoClickListener mListener;

        private List<Photo> mItems = new ArrayList<>();

        public PhotoAdapter() {
        }

        public PhotoAdapter(OnPhotoClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Photo> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo, parent, false);
            final PhotoHolder viewHolder = new PhotoHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Photo item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.onPhotoClicked(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            Photo item = mItems.get(position);
            // TODO : 데이터를 뷰홀더에 표시하시오
            holder.binding.setPhoto(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class PhotoHolder extends RecyclerView.ViewHolder {
            // TODO : 뷰홀더 완성하시오
            ItemPhotoBinding binding;

            public PhotoHolder(@NonNull View itemView) {
                super(itemView);
                // TODO : 뷰홀더 완성하시오
                binding = ItemPhotoBinding.bind(itemView);
            }
        }
    }

}

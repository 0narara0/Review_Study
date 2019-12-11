package com.narara.review_study.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narara.review_study.R;
import com.narara.review_study.databinding.FragmentPhotoDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment {


    public PhotoDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentPhotoDetailBinding binding = FragmentPhotoDetailBinding.bind(view);

        String url = PhotoDetailFragmentArgs.fromBundle(getArguments()).getUrl();

        binding.setUrl(url);

    }
}

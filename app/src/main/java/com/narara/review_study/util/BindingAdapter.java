package com.narara.review_study.util;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.narara.review_study.models.Photo;
import com.narara.review_study.ui.PhotoListFragment;

import java.util.List;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("items")
    public static void recyclerView_items(RecyclerView recyclerView, List<Photo> items) {

        if (items != null && recyclerView.getAdapter() instanceof PhotoListFragment.PhotoAdapter) {
            PhotoListFragment.PhotoAdapter adapter = (PhotoListFragment.PhotoAdapter) recyclerView.getAdapter();

            adapter.setItems(items);
        }
    }

    @androidx.databinding.BindingAdapter("url")
    public static void imageView_url(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }
}

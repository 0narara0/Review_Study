package com.narara.review_study.repository;

import com.narara.review_study.models.Album;
import com.narara.review_study.models.Photo;
import com.narara.review_study.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("users")
    public Call<List<User>> getUsers();

    @GET("albums")
    public Call<List<Album>> getAlbums(@Query("userId") int userId);

    @GET("photos")
    public Call<List<Photo>> getPhotos(@Query("albumId") int albumId);
}

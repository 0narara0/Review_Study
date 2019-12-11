package com.narara.review_study.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.narara.review_study.models.Album;
import com.narara.review_study.repository.JsonPlaceHolderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumListViewModel extends ViewModel {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(JsonPlaceHolderService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderService service = retrofit.create(JsonPlaceHolderService.class);

    public MutableLiveData<List<Album>> albums = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> isProgressing = new MutableLiveData<>();


    public void fetchAlbums(int userId) {
        isProgressing.setValue(true);
        service.getAlbums(userId).enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.body() != null ){
                    albums.setValue(response.body());
                }
                isProgressing.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorMessage.setValue(t.getLocalizedMessage());
                isProgressing.setValue(false);
            }
        });

    }

}

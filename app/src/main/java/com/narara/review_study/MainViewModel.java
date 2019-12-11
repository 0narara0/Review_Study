package com.narara.review_study;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.narara.review_study.models.Album;
import com.narara.review_study.models.Photo;
import com.narara.review_study.models.User;
import com.narara.review_study.repository.JsonPlaceHolderService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(JsonPlaceHolderService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderService service = retrofit.create(JsonPlaceHolderService.class);

    public MutableLiveData<List<User>> users = new MutableLiveData<>();
    public MutableLiveData<List<Photo>> photos = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> isProgressing = new MutableLiveData<>();

    public void fetchUsers() {
        isProgressing.setValue(true);
        service.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null ){
                    users.setValue(response.body());
                }
                isProgressing.setValue(false);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                errorMessage.setValue(t.getLocalizedMessage());
                isProgressing.setValue(false);
            }
        });
    }

    public void fetchPhotos(int albumId) {
        isProgressing.setValue(true);
        service.getPhotos(albumId).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.body() != null ){
                    photos.setValue(response.body());
                    isProgressing.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                errorMessage.setValue(t.getLocalizedMessage());
                isProgressing.setValue(false);
            }
        });
    }

}

package com.tech.arinzedroid.haxitaapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class AppRepo {

    private static String BASE_URL = "https://peepandearn.com/";
    private ApiService apiService;

    public AppRepo(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<DataModel> getData(){
        final MutableLiveData<DataModel> dataModelMutableLiveData = new MutableLiveData<>();
        apiService.getData().enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(@NonNull Call<DataModel> call,@NonNull Response<DataModel> response) {
                if(response.isSuccessful()){
                    dataModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataModel> call,@NonNull Throwable t) {

            }
        });
        return dataModelMutableLiveData;
    }



    private interface ApiService{
        @GET("/api/android-dev/test/content")
        Call<DataModel> getData();

    }
}

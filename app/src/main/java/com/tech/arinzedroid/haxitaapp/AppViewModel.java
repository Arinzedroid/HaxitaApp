package com.tech.arinzedroid.haxitaapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class AppViewModel extends ViewModel {

    private AppRepo appRepo;
    private LiveData<DataModel> data;

    public AppViewModel(){
        appRepo = new AppRepo();
    }

    public LiveData<DataModel> getData(){
        if(data == null)
            data = appRepo.getData();
        return data;
    }

}

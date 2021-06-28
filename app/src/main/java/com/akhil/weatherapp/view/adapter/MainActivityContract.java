package com.akhil.weatherapp.view.adapter;


import android.content.SharedPreferences;

import com.akhil.weatherapp.model.DataModel;

import java.util.List;

public interface MainActivityContract {
    interface View extends BaseView<Presenter> {
        void showAllDetails(List<DataModel> datas);
    }

    interface Presenter {
        void onSearch(String location);
        boolean saveToSharedPreferences(SharedPreferences sharedPreferences, String location);
    }
}

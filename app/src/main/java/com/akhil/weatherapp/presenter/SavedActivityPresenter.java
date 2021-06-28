package com.akhil.weatherapp.presenter;

import android.content.SharedPreferences;

import com.akhil.weatherapp.model.SavedDataModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SavedActivityPresenter {

    private final View view;
    SavedDataModel savedDataModel;
    ArrayList<SavedDataModel> datas;

    public SavedActivityPresenter(View view) {
        this.view = view;
    }

    public void initData(HashSet<String> locations) {
        datas = new ArrayList<>();

        for (String location : locations) {
            savedDataModel = new SavedDataModel(location);
            datas.add(savedDataModel);
        }

        view.addDatas(datas);
    }

    public interface View {
        void addDatas(ArrayList<SavedDataModel> datas);
    }

    public HashSet<String> getSharedPreferences(SharedPreferences sharedPreferences, String key) {
        Set<String> savedLocations = sharedPreferences.getStringSet(key, new HashSet<>());
        return new HashSet<>(savedLocations);
    }
}

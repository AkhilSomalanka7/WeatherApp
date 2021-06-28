package com.akhil.weatherapp.presenter;

import android.content.SharedPreferences;

import com.akhil.weatherapp.model.DataModel;
import com.akhil.weatherapp.view.adapter.MainActivityContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    MainActivityContract.View view;
    DataModel dataModel;
    ArrayList<DataModel> datas;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        datas = new ArrayList<>();
    }

    public boolean initData(String location) {
        location = location.replace(" ", "%20");
        JSONObject weatherJson = getWeather(location);
        datas = new ArrayList<>();

        try {
            dataModel = new DataModel("Location:", weatherJson.getJSONObject("location").getString("name"));
            datas.add(dataModel);

            dataModel = new DataModel("Country:", weatherJson.getJSONObject("location").getString("country"));
            datas.add(dataModel);

            weatherJson = weatherJson.getJSONObject("current");

            dataModel = new DataModel("Last Updated:", weatherJson.getString("observation_time"));
            datas.add(dataModel);


            dataModel = new DataModel("Temperature:", weatherJson.getInt("temperature") + "°C");
            datas.add(dataModel);

            dataModel = new DataModel("Feels Like:", weatherJson.getInt("feelslike") + "°C");
            datas.add(dataModel);

            dataModel = new DataModel("Wind Speed:", weatherJson.getInt("wind_speed") + "KM/Hr");
            datas.add(dataModel);

            dataModel = new DataModel("Wind Degree:", Integer.toString(weatherJson.getInt("wind_degree")));
            datas.add(dataModel);

            dataModel = new DataModel("Wind Direction:", weatherJson.getString("wind_dir"));
            datas.add(dataModel);

            dataModel = new DataModel("Pressure:", weatherJson.getInt("pressure") + "MB");
            datas.add(dataModel);

            dataModel = new DataModel("Precipitation:", weatherJson.getInt("precip") + "mm");
            datas.add(dataModel);

            dataModel = new DataModel("Humidity:", weatherJson.getInt("humidity") + "%");
            datas.add(dataModel);

            dataModel = new DataModel("Cloud cover:", weatherJson.getInt("cloudcover") + "%");
            datas.add(dataModel);

            dataModel = new DataModel("UV Index:", weatherJson.getInt("uv_index") + "");
            datas.add(dataModel);

            dataModel = new DataModel("Visibility:", weatherJson.getInt("visibility") + "KM");
            datas.add(dataModel);

            return true;
        } catch (Exception e) {
            dataModel = new DataModel("Invalid Location", "");
            datas.add(dataModel);

            return false;
        }
    }

    @Override
    public void onSearch(String location) {
        initData(location);
        view.showAllDetails(datas);
    }

    public static class NetworkTask implements Callable<String> {
        String urlString;

        public NetworkTask(String input) {
            this.urlString = input;
        }

        @Override
        public String call() {
            StringBuilder resultDownload = new StringBuilder();
            try {
                URL url = new URL(urlString);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                String data = bufferedReader.readLine();
                while (data != null) {
                    resultDownload.append(data);
                    data = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultDownload.toString();
        }
    }

    public static class TaskRunner {
        public String runTask(String input) {
            String result = "";
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            NetworkTask networkTask = new NetworkTask(input);
            List<Future<String>> futureList = new ArrayList<>();
            try {
                Future<String> future = executorService.submit(networkTask);
                futureList.add(future);
                result = futureList.get(0).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public JSONObject getWeather(String location) {
        TaskRunner taskRunner = new TaskRunner();
        String accessKey = "6fc56597afb785b40767bbf70a9b9bea";
        String jsonString = taskRunner.runTask("http://api.weatherstack.com/current?access_key=" + accessKey + "&query=" + location);
        try {
            if (jsonString.equals("")) {
                return null;
            } else {
                return new JSONObject(jsonString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveToSharedPreferences(SharedPreferences sharedPreferences, String location) {
        if (location.equals("")) {
            return false;
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> savedLocations = sharedPreferences.getStringSet("locations", new HashSet<>());
            Set<String> savedLocationsEditable = new HashSet<>(savedLocations);
            savedLocationsEditable.add(location);
            editor.putStringSet("locations", savedLocationsEditable);
            editor.apply();
            return true;
        }
    }
}

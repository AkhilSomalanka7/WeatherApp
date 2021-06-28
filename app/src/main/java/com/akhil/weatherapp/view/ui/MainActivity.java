package com.akhil.weatherapp.view.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.akhil.weatherapp.R;
import com.akhil.weatherapp.model.DataModel;
import com.akhil.weatherapp.presenter.MainActivityPresenter;
import com.akhil.weatherapp.view.adapter.MainActivityContract;
import com.akhil.weatherapp.view.adapter.MainDataListAdapter;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "mainActivity";
    RecyclerView recyclerView;
    MainActivityContract.Presenter mainPresenter;
    MainDataListAdapter adapter;
    TextInputEditText getLocation;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, SavedLocations.class);

        //configuring recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //setting adapter to recycler view
        adapter = new MainDataListAdapter();
        recyclerView.setAdapter(adapter);

        //creating instance of presenter class
        this.setPresenter(new MainActivityPresenter(this));

        //calling initData method of presenter class in which we added dtudent record
        getLocation = findViewById(R.id.locationTextInput);

        //initializing shared preferences
        sharedPreferences = this.getSharedPreferences(TAG, Context.MODE_PRIVATE);

        getLocation.setText(getIntent().getStringExtra("location"));
        mainPresenter.onSearch(Objects.requireNonNull(getLocation.getText()).toString());

        getLocation.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                mainPresenter.onSearch(textView.getText().toString());
            }
            return false;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.saveActionBar) {
            String location = Objects.requireNonNull(getLocation.getText()).toString();
            if (mainPresenter.saveToSharedPreferences(sharedPreferences, location)) {
                Toast.makeText(this, "Location Saved", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Enter a location", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (item.getItemId() == R.id.savedActionBar) {
            intent.putExtra("fromMain", true);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void showAllDetails(List<DataModel> datas) {
        adapter.submitList(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {
        this.mainPresenter = presenter;
    }
}
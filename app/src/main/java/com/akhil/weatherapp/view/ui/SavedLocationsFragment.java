package com.akhil.weatherapp.view.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.akhil.weatherapp.R;
import com.akhil.weatherapp.model.SavedDataModel;
import com.akhil.weatherapp.presenter.SavedActivityPresenter;
import com.akhil.weatherapp.view.adapter.RecyclerViewClickInterface;
import com.akhil.weatherapp.view.adapter.SavedDataListAdapter;

import java.util.ArrayList;
import java.util.HashSet;

public class SavedLocationsFragment extends Fragment implements SavedActivityPresenter.View, RecyclerViewClickInterface {
    RecyclerView recyclerView;
    SavedDataListAdapter adapter;
    SavedActivityPresenter savedPresenter;
    TextInputEditText getLocation;
    HashSet<String> locations;
    boolean fromMain;
    Intent intent;
    SharedPreferences sharedPreferences;

    public SavedLocationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saved_locations, container, false);

        recyclerView = v.findViewById(R.id.savedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new SavedDataListAdapter(this);
        recyclerView.setAdapter(adapter);

        savedPresenter = new SavedActivityPresenter((SavedActivityPresenter.View) this);

        getLocation = v.findViewById(R.id.locationTextInput);

        sharedPreferences = requireActivity().getSharedPreferences("mainActivity", Context.MODE_PRIVATE);

        locations = savedPresenter.getSharedPreferences(sharedPreferences, "locations");

        Intent tempIntent = requireActivity().getIntent();
        fromMain = tempIntent.getBooleanExtra("fromMain", false);

        intent = new Intent(requireActivity(), MainActivity.class);

        if (locations.size() == 0) {
            TextView textView = (TextView) v.findViewById(R.id.textViewSaved);
            if (fromMain) {
                textView.setText("No Saved Locations");
            } else {
                textView.setVisibility(View.GONE);
            }
        } else {
            savedPresenter.initData(locations);
        }

        CardView cardView = v.findViewById(R.id.cardViewSaved);

        if (fromMain) {
            cardView.setVisibility(View.GONE);
        }


        getLocation = v.findViewById(R.id.locationTextInputSavedLocations);
        getLocation.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                callMainThroughIntent(textView.getText().toString());
            }
            return false;
        });
        return v;
    }

    @Override
    public void onClick(int position) {
        callMainThroughIntent(locations.toArray()[position].toString());
    }

    void callMainThroughIntent(String location) {
        intent.putExtra("location", location);
        startActivity(intent);
    }

    @Override
    public void addDatas(ArrayList<SavedDataModel> datas) {
        adapter.submitList(datas);
        adapter.notifyDataSetChanged();
    }
}
package com.akhil.weatherapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.akhil.weatherapp.R;
import com.akhil.weatherapp.model.DataModel;

public class MainDataListAdapter extends ListAdapter<DataModel, MainDataListAdapter.ViewHolder> {

    public MainDataListAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<DataModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataModel>() {
        @Override
        public boolean areItemsTheSame(DataModel oldItem, DataModel newItem) {
            return oldItem.getAttribute().equals(newItem.getValue());
        }
        @Override
        public boolean areContentsTheSame(DataModel oldItem, DataModel newItem) {
            return oldItem.getAttribute().equals(newItem.getAttribute()) &&
                    oldItem.getValue().equals(newItem.getValue());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.attribute.setText(getItem(position).getAttribute());
        holder.value.setText(String.valueOf(getItem(position).getValue()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView attribute, value;
        ViewHolder(View itemView) {
            super(itemView);
            attribute = itemView.findViewById(R.id.tv_attribute);
            value = itemView.findViewById(R.id.tv_value);
        }
    }
}
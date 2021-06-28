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
import com.akhil.weatherapp.model.SavedDataModel;

public class SavedDataListAdapter extends ListAdapter<SavedDataModel, SavedDataListAdapter.ViewHolder> {

    private final RecyclerViewClickInterface mRecyclerViewClickInterface;

    public SavedDataListAdapter(RecyclerViewClickInterface recyclerViewClickInterface) {
        super(DIFF_CALLBACK);
        this.mRecyclerViewClickInterface = recyclerViewClickInterface;
    }
    private static final DiffUtil.ItemCallback<SavedDataModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<SavedDataModel>() {
        @Override
        public boolean areItemsTheSame(SavedDataModel oldItem, SavedDataModel newItem) {
            return oldItem.getAttribute().equals(newItem.getAttribute());
        }
        @Override
        public boolean areContentsTheSame(SavedDataModel oldItem, SavedDataModel newItem) {
            return oldItem.getAttribute().equals(newItem.getAttribute());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_recycler_view_item, parent, false);
        return new ViewHolder(view, mRecyclerViewClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.attribute.setText(getItem(position).getAttribute());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView attribute;
        RecyclerViewClickInterface recyclerViewClickInterface;

        ViewHolder(View itemView, RecyclerViewClickInterface recyclerViewClickInterface) {
            super(itemView);
            attribute = itemView.findViewById(R.id.saved_tv_attribute);
            this.recyclerViewClickInterface = recyclerViewClickInterface;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickInterface.onClick(getAdapterPosition());
        }
    }
}
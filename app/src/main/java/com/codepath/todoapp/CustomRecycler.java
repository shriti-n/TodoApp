package com.codepath.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.CustomViewHolder> {


    List<String> items;
    OnLongClickListener customLongClickListener;
    OnClickListener customOnClickListener;

    public CustomRecycler(List<String> items, OnLongClickListener onLongClickListener, OnClickListener onClickListener) {
        this.items = items;
        this.customLongClickListener = onLongClickListener;
        this.customOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_row_item, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView itemHolder;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            itemHolder = itemView.findViewById(R.id.txtItem);
        }

        public void bind(String item) {
            itemHolder.setText(item);

            itemHolder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    customLongClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
            itemHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customOnClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }

    }
}

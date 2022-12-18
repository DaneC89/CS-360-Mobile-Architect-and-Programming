package com.example.eventtrackerapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalanderAdapter extends RecyclerView.Adapter<CalanderViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalanderAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener){
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalanderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calander_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if(days.size() > 15) { // month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        }
        else { // weekly view
            layoutParams.height = (int)(parent.getHeight());
        }
        return new CalanderViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalanderViewHolder holder, int position) {
        final LocalDate date = days.get(position);

        if(date == null) {
            holder.dayOfMonth.setText("");
        }
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalenderUtils.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void OnItemClick(int position, LocalDate date);
    }
}

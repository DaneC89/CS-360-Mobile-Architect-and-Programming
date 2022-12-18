package com.example.eventtrackerapp;

import static com.example.eventtrackerapp.CalenderUtils.daysInMonthArray;
import static com.example.eventtrackerapp.CalenderUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainEventTracker extends AppCompatActivity implements CalanderAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event_tracker);
        initWidgets();
        CalenderUtils.selectedDate = LocalDate.now();
        setMonthView();

    }

    private void initWidgets() {
        calenderRecyclerView = findViewById(R.id.calanderRecView);
        monthYearText = findViewById(R.id.monthYear);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalenderUtils.selectedDate);

        CalanderAdapter calanderAdapter = new CalanderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calanderAdapter);
    }



    public void previousMonth(View view){
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view){
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void OnItemClick(int position, LocalDate date) {
        if(date != null) {
            CalenderUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyView.class));
    }
}
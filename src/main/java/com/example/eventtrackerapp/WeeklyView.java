package com.example.eventtrackerapp;

import static com.example.eventtrackerapp.CalenderUtils.daysInWeekArray;
import static com.example.eventtrackerapp.CalenderUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeeklyView extends AppCompatActivity implements CalanderAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;
    private ListView eventListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        initWidgets();
        setWeekView();

    }
    private void initWidgets() {
        calenderRecyclerView = findViewById(R.id.calanderRecView);
        monthYearText = findViewById(R.id.monthYear);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate(CalenderUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalenderUtils.selectedDate);
        CalanderAdapter calanderAdapter = new CalanderAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calanderAdapter);
        setEventAdapter();
    }

    public void previousWeek(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeek(View view) {
        CalenderUtils.selectedDate = CalenderUtils.selectedDate.plusMonths(1);
        setWeekView();
    }

    @Override
    public void OnItemClick(int position, LocalDate date) {
           CalenderUtils.selectedDate = date;
           setWeekView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDay(CalenderUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // click on event to edit or delete
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(WeeklyView.this, updateDeleteEvent.class));
            }
        });
        //Delete event dialog on long press
        eventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(WeeklyView.this)
                        .setTitle("Do you want to DELETE this even?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dailyEvents.remove(i);
                                eventAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                return false;
            }
        });
    }
    // starts new event
    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEdit.class));

    }

}